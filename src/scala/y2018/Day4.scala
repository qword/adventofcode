package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day4 {
  def main(args: Array[String]): Unit = {
    var list = ListBuffer.empty[String]

    Source.fromFile("input/2018/day4").getLines.foreach(line => {
      list += line
    })

    list = list.sorted
    val map = scala.collection.mutable.HashMap.empty[String, Int]
    val minutesAsleep = scala.collection.mutable.HashMap.empty[String, Int]

    var currentGuard = ""
    var startMinute = 0
    list.foreach(line => {
      val tokens = line.split(" ")
      if (tokens(2) == "Guard") {
        currentGuard = tokens(3) substring(1)
      }
      if (tokens(2) == "falls") {
        startMinute = tokens(1) substring(3,5) toInt
      }
      if (tokens(2) == "wakes") {
        val endMinute = tokens(1).substring(3,5).toInt

        for (x <- startMinute until endMinute) {
          val index = currentGuard + "-" + x
          if (map.contains(index)) {
            map += (index -> (map(index) + 1))
          } else {
            map += (index -> 1)
          }
        }

        val asleep = endMinute - startMinute
        if (minutesAsleep.contains(currentGuard)) {
          minutesAsleep += (currentGuard -> (minutesAsleep(currentGuard) + asleep))
        } else {
          minutesAsleep += (currentGuard -> asleep)
        }
      }
    })

    val sleepyGuard = minutesAsleep.maxBy(_._2)._1

    val result1 = map.filter(p => p._1.startsWith(sleepyGuard+"-")).maxBy(_._2)
    val result2 = map.maxBy(_._2)

    println(s"Part1 (guard id - minute): ${result1._1}")
    println(s"Part2 (guard id - minute): ${result2._1}")
  }
}
