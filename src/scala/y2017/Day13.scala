package y2017

import scala.io.Source

object Day13 {

  def main(args: Array[String]): Unit = {
    var totalRisk = 0

    Source.fromFile("input/2017/day13").getLines.foreach(line => {
      val tokens = line.split(": ")

      val depth = tokens(0) toInt
      val range = tokens(1) toInt

      if (depth % ((range - 1) * 2) == 0) {
        totalRisk += (depth * range)
      }
    })
    println(s"Total risk: $totalRisk")

  }
}

object Day13B {

  def main(args: Array[String]): Unit = {

    var delay = 11

    while (true) {
      var totalRisk = 0

      // brute force method, really ugly. There must be a better way
      Source.fromFile("input/2017/day13").getLines.foreach(line => {
        val tokens = line.split(": ")

        val depth = tokens(0) toInt
        val range = tokens(1) toInt

        if ((depth + delay) % ((range - 1) * 2) == 0) {
          totalRisk += 1
        }
      })

      if (totalRisk == 0) {
        println(s"Delay: $delay")
        System.exit(0)
      } else {
        delay += 1
      }
    }
  }
}
