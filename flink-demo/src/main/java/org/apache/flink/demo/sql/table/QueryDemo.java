package org.apache.flink.demo.sql.table;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jie Zhao
 * @date 2023/1/4 21:58
 */
public class QueryDemo {

    public static void main(String[] args) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();

        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(environment, environmentSettings);
    }
}
