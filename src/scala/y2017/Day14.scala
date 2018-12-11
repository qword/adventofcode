package y2017

import scala.io.Source

object Day14 {

  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2017/day14").getLines.next
    val grid = Array.ofDim[String](128)

    var count = 0
    // build grid
    for (x <- 0 until 128) {
      val hash = KnotHash.hash(s"$input-$x")
      val binary = hexToBin(hash)
      count += binary.count(_ == '1')

      grid(x) = binary
    }
    println(s"Squares used: $count")

    def mark(x: Int, y: Int): Unit = {
      val char = grid(x)(y)

      if (char == '1') { // mark this and look for adjacent squares
        var tmp = ""
        if (y > 0) tmp = grid(x).substring(0, y)
        tmp += "x"
        if (y < 127) tmp += grid(x).substring(y + 1)
        grid(x) = tmp

        if (x > 0) mark(x - 1, y)
        if (y > 0) mark(x, y - 1)
        if (x < 127) mark(x + 1, y)
        if (y < 127) mark(x, y + 1)
      }
    }

    var current = 0
    for (x <- 0 until 128)
      for (y <- 0 until 128)
        if (grid(x)(y) == '1') {
          current += 1
          mark(x, y)
        }

    println(s"Regions found: $current")
  }

  def hexToBin(input: String): String = {
    BigInt(input, 16).toString(2).reverse.padTo(128, '0').reverse
  }
}
