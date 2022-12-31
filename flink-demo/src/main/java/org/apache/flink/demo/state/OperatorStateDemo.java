package org.apache.flink.demo.state;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.concurrent.TimeUnit;

/**
 * @author jie zhao
 * @date 2022/12/23 11:03
 */
public class OperatorStateDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new OperatorStateSource())
                .print();
        env.execute();
    }

    public static class OperatorStateSource implements SourceFunction<Integer>, CheckpointedFunction {

        private int counter;
        private ListState<Integer> counterState;

        @Override
        public void run(SourceContext<Integer> ctx) throws Exception {
            while (true) {
                counter++;
                ctx.collect(counter);
                TimeUnit.SECONDS.sleep(1);

                if (counter == 10) {
                    throw new IllegalStateException();
                }
            }
        }

        @Override
        public void cancel() {

        }

        @Override
        public void snapshotState(FunctionSnapshotContext context) throws Exception {
            counterState.clear();
            counterState.add(counter);
        }

        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {

        }
    }
}
