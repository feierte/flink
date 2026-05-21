package org.apache.flink.streaming.demo.calc;

import org.apache.flink.runtime.operators.coordination.CoordinationRequest;
import org.apache.flink.runtime.operators.coordination.CoordinationRequestHandler;
import org.apache.flink.runtime.operators.coordination.CoordinationResponse;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinatorHolder;
import org.apache.flink.runtime.operators.coordination.OperatorEvent;

import javax.annotation.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * @author Jie Zhao
 * @date 2024/12/21 11:49
 */
public class CalculationOperatorCoordinator implements OperatorCoordinator, CoordinationRequestHandler {
    @Override
    public void start() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void handleEventFromOperator(
            int subtask,
            int attemptNumber,
            OperatorEvent event) throws Exception {

    }

    @Override
    public void checkpointCoordinator(
            long checkpointId,
            CompletableFuture<byte[]> resultFuture) throws Exception {

    }

    @Override
    public void notifyCheckpointComplete(long checkpointId) {

    }

    @Override
    public void resetToCheckpoint(
            long checkpointId,
            @Nullable byte[] checkpointData) throws Exception {

    }

    @Override
    public void subtaskReset(int subtask, long checkpointId) {

    }

    @Override
    public void executionAttemptFailed(int subtask, int attemptNumber, @Nullable Throwable reason) {

    }

    @Override
    public void executionAttemptReady(int subtask, int attemptNumber, SubtaskGateway gateway) {

    }

    @Override
    public CompletableFuture<CoordinationResponse> handleCoordinationRequest(CoordinationRequest request) {
        return null;
    }
}
