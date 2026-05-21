package org.apache.flink.streaming.demo.calc;

import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.streaming.api.operators.AbstractStreamOperatorFactory;
import org.apache.flink.streaming.api.operators.ChainingStrategy;
import org.apache.flink.streaming.api.operators.CoordinatedOperatorFactory;
import org.apache.flink.streaming.api.operators.OneInputStreamOperatorFactory;
import org.apache.flink.streaming.api.operators.StreamOperator;
import org.apache.flink.streaming.api.operators.StreamOperatorFactory;
import org.apache.flink.streaming.api.operators.StreamOperatorParameters;

/**
 * @author Jie Zhao
 * @date 2024/12/21 13:07
 */
public class CalculationOperatorFactory extends AbstractStreamOperatorFactory<CalculationEvent>
        implements CoordinatedOperatorFactory<CalculationEvent>,
        OneInputStreamOperatorFactory<CalculationEvent, CalculationEvent> {
    @Override
    public <T extends StreamOperator<CalculationEvent>> T createStreamOperator(
            StreamOperatorParameters<CalculationEvent> parameters) {
        return null;
    }

    @Override
    public Class<? extends StreamOperator> getStreamOperatorClass(ClassLoader classLoader) {
        return CalculationOperator.class;
    }

    @Override
    public OperatorCoordinator.Provider getCoordinatorProvider(
            String operatorName,
            OperatorID operatorID) {
        return new CalculationOperatorCoordinatorProvider(operatorName, operatorID);
    }
}
