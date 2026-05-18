package org.apache.flink.streaming.demo.calc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.flink.runtime.operators.coordination.CoordinationRequest;

/**
 * @author Jie Zhao
 * @date 2024/12/21 11:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionChangeCoordinationRequest implements CoordinationRequest {

    private ExpressionEvent expressionEvent;
}
