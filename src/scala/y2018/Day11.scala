package y2018

import scala.io.Source

object Day11 {
  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2018/day11").getLines.next.toInt
    val grid = Array.ofDim[Int](300, 300)

    def cellVal(x: Int, y: Int): Int = {
      var rackId = x + 10
      var powerLevel = y * rackId
      powerLevel += input
      powerLevel *= rackId

      var digit = 0
      if (powerLevel >= 100) {
        digit = (powerLevel / 100) % 10
      }
      digit - 5
    }

    for (x <- 1 until 300) {
      for (y <- 1 until 300) {
        grid(x)(y) = cellVal(x, y)
      }
    }

    var maxVal = Int.MinValue
    var maxValPos = ""

    for (square <- 0 to 300) {
      for (x <- 1 until 300 - square) {
        for (y <- 1 until 300 - square) {
          // start from tof left

          var tot = 0
          for (a <- 0 until square)
            for (b <- 0 until square) {
              tot += grid(x + a)(y + b)
            }

          if (tot > maxVal) {
            maxVal = tot
            maxValPos = s"$x,$y,$square"
          }
        }
      }
      if (square == 3) println(s"Max value found (square 3): $maxVal at $maxValPos")
//      else println(s"Analyzed square: $square")
    }
    println(s"Max value found (any square): $maxVal at $maxValPos")
  }
}
