package org.apache.flink.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author Jie Zhao
 * @date 2022/1/22 13:09
 */
public class WordCountStream {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream = executionEnvironment.fromElements(
                "Hello World",
                "Hello Java");

        SingleOutputStreamOperator<Tuple2<String, Integer>> result = dataStream
                .flatMap(new LineSplitter())
                .keyBy(0)
                .sum(1);

        result.print();
        executionEnvironment.execute();

        System.out.println("Hello World!!!");
    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // 将文本分割
            String[] tokens = value.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}
