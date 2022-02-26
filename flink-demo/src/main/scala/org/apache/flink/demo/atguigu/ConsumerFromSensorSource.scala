package org.apache.flink.demo.atguigu

import org.apache.flink.streaming.api.scala._

/**
 * @author Jie Zhao
 * @date 2022/2/19 19:49
 */
object ConsumerFromSensorSource {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[SensorReading] = env.addSource(new SensorSource)
    stream.print

    env.execute
  }
}

