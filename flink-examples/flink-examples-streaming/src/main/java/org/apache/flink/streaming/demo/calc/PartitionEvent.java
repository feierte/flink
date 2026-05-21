package org.apache.flink.streaming.demo.calc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jie Zhao
 * @date 2024/12/21 11:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartitionEvent implements CalculationEvent {

    private int partitionId;
    private CalculationEvent calculationEvent;
}
