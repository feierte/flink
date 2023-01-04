package org.apache.flink.demo.sql.table;

import org.apache.flink.connector.datagen.table.DataGenConnectorOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableDescriptor;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * @author jie zhao
 * @date 2022/12/31 12:54
 */
public class TableDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(
                env,
                environmentSettings);

        // 使用 table API 创建源表
        tableEnvironment.createTemporaryTable(
                "source_table",
                TableDescriptor.forConnector("datagen")
                        .schema(Schema.newBuilder()
                                .column("f0", DataTypes.STRING())
                                .build())
                        // .option(DataGenConnectorOptions.ROWS_PER_SECOND, 10L)
                        .build());

        // 使用 sql 方式创建目标表
        String sinkTableSql = "CREATE TEMPORARY TABLE sink_table WITH ('connector' = 'blackhole') LIKE source_table";
        TableResult sinkTable = tableEnvironment.executeSql(sinkTableSql);

        // 使用 table api 方式读取数据
        Table sourceTable = tableEnvironment.from("source_table");
        sourceTable.printSchema();

        // 将 Table 对象转换为 DataStream 对象
        DataStream<Row> sourceDataStream = tableEnvironment.toDataStream(sourceTable);
        sourceDataStream.print("表中的数据======>");

        // 将源表数据写到目标表中
        sourceTable.executeInsert("sink_table");

        env.execute();
    }
}
