package org.apache.flink.demo

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 * @author Jie Zhao
 * @date 2022/1/22 16:47
 */
object WordCount {

  def main(args: Array[String]): Unit ={
    //创建流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._
    //接收socket文本流
    // 127.0.0.1或者IPv4地址均可
    // val textDstream: DataStream[String] = env.socketTextStream("127.0.0.1", 7777)
    val textDstream: DataStream[String] = env.fromElements("Hello Java", "Hello World")

    //flatMap和Map需要引用的隐式转换
    import org.apache.flink.api.scala._
    val dataStream: DataStream[(String, Int)] = textDstream.flatMap(_.split(","))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .sum(1)

    dataStream.print().setParallelism(1) //1代表并行度,不指定则默认为电脑核数
    env.execute("Socket stream word count")

  }

}
