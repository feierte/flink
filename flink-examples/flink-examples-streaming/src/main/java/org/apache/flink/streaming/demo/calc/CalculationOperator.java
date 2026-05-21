package org.apache.flink.streaming.demo.calc;

import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.jobgraph.tasks.TaskOperatorEventGateway;
import org.apache.flink.runtime.operators.coordination.CoordinationRequest;
import org.apache.flink.runtime.operators.coordination.CoordinationResponse;
import org.apache.flink.streaming.api.graph.StreamConfig;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.operators.Output;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.tasks.StreamTask;
import org.apache.flink.util.SerializedValue;

import java.util.concurrent.CompletableFuture;

/**
 * @author Jie Zhao
 * @date 2024/12/21 10:44
 */
public class CalculationOperator extends AbstractStreamOperator<CalculationEvent>
        implements OneInputStreamOperator<CalculationEvent, CalculationEvent> {

    private TaskOperatorEventGateway operatorCoordinator;
    // 下游算子的并行度
    // 在当前算子中无法拿到下游算子的并行度，所以需要由外部传入
    private int downstreamParallelism;

    public CalculationOperator(int downstreamParallelism) {
        this.downstreamParallelism = downstreamParallelism;
    }

    @Override
    public void setup(
            StreamTask<?, ?> containingTask,
            StreamConfig config,
            Output<StreamRecord<CalculationEvent>> output) {
        super.setup(containingTask, config, output);
        operatorCoordinator = containingTask
                .getEnvironment()
                .getOperatorCoordinatorEventGateway();
    }

    @Override
    public void processElement(StreamRecord<CalculationEvent> element) throws Exception {
        CalculationEvent value = element.getValue();
        if (value instanceof ExpressionEvent) {
            // 向 OperatorCoordinator 发送表达式变更通知
            ExpressionEvent expressionEvent = (ExpressionEvent) value;
            ExpressionChangeCoordinationRequest request = new ExpressionChangeCoordinationRequest(
                    expressionEvent);
            CompletableFuture completableFuture = this.operatorCoordinator.sendRequestToCoordinator(
                    getOperatorID(),
                    new SerializedValue<>(request));

            // 向下游算子广播 flush 通知
            broadcast(value);

            // 向 OperatorCoordinator 发送放开阻塞通知
            UnblockingCoordinationRequest unblockingCoordinationRequest = new UnblockingCoordinationRequest();
            CompletableFuture<CoordinationResponse> coordinationResponseCompletableFuture = this.operatorCoordinator.sendRequestToCoordinator(
                    getOperatorID(),
                    new SerializedValue<>(unblockingCoordinationRequest));
            coordinationResponseCompletableFuture.get();
        } else {
            // 数据直接向下游传递
            output.collect(new StreamRecord<>(value));
        }
    }

    public void broadcast(CalculationEvent event) {

        for (int i = 0; i < this.downstreamParallelism; i++) {
            PartitionEvent partitionEvent = new PartitionEvent(i, event);
            output.collect(new StreamRecord<>(partitionEvent));
        }
    }
}
