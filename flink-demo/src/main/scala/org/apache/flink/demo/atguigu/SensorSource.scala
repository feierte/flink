package org.apache.flink.demo.atguigu

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

import java.util.Calendar
import scala.util.Random

/**
 * @author Jie Zhao
 * @date 2022/2/19 19:40
 */
class SensorSource extends RichParallelSourceFunction[SensorReading] {

  // 表示数据源是否正常运行
  var running: Boolean = true

  override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {
    val rand = new Random

    var curFTemp = (1 to 10).map(
      // 使用高斯噪声产生随机温度值
      i => ("sensor_" + i, (rand.nextGaussian() * 20))
    )

    while(running) {
      curFTemp = curFTemp.map(
        t => (t._1, t._2 + (rand.nextGaussian() * 0.5))
      )

      // 产生时间戳
      val curTime = Calendar.getInstance.getTimeInMillis

      curFTemp.foreach(t => ctx.collect(SensorReading(t._1, curTime, t._2)))

      Thread.sleep(100)
    }
  }

  override def cancel(): Unit = running = false
}
