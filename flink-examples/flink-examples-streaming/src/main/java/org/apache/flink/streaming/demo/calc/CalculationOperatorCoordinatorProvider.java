package org.apache.flink.streaming.demo.calc;

import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;

/**
 * @author Jie Zhao
 * @date 2024/12/21 13:18
 */
public class CalculationOperatorCoordinatorProvider implements OperatorCoordinator.Provider {

    private String operatorName;
    private OperatorID operatorID;

    public CalculationOperatorCoordinatorProvider(String operatorName, OperatorID operatorID) {
        this.operatorName = operatorName;
        this.operatorID = operatorID;
    }

    @Override
    public OperatorID getOperatorId() {
        return this.operatorID;
    }

    @Override
    public OperatorCoordinator create(OperatorCoordinator.Context context) throws Exception {
        return null;
    }
}
