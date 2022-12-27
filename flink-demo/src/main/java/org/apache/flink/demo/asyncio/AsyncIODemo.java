package org.apache.flink.demo.asyncio;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Jie Zhao
 * @date 2022/12/17 18:52
 */
public class AsyncIODemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> socketTextStream = env.socketTextStream("zj", 9999);

        SingleOutputStreamOperator<Integer> idStream = socketTextStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) throws Exception {
                return Integer.parseInt(value);
            }
        });

        SingleOutputStreamOperator<Tuple3<Integer, String, String>> asyncStream = AsyncDataStream.unorderedWait(
                idStream,
                new MysqlAsyncFunction(),
                30000,
                TimeUnit.SECONDS);

        asyncStream.print();
        env.execute();
    }

    public static class MysqlAsyncFunction extends RichAsyncFunction<Integer, Tuple3<Integer, String, String>> {

        private String url = "jdbc:mysql://zj:3306/flink";
        private String username = "root";
        private String password = "admin";
        private Connection connection;
        private PreparedStatement preparedStatement;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement("select * from user where id = ?");
        }

        @Override
        public void asyncInvoke(
                Integer input,
                ResultFuture<Tuple3<Integer, String, String>> resultFuture) throws Exception {

            CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            preparedStatement.setInt(1, input);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            TimeUnit.SECONDS.sleep(5); // 模仿查询耗时操作
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String name = resultSet.getString("name");
                                String password = resultSet.getString("password");
                                return Tuple3.of(id, name, password);
                            }
                        } catch (Exception throwables) {
                            throwables.printStackTrace();
                        }
                        return Tuple3.of(0, "未知", "未知");
                    })
                    .thenAccept(result -> {
                        resultFuture.complete(Collections.singleton(result));
                    });
        }
    }
}
