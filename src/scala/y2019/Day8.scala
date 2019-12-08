package y2019

import scala.io.Source

object Day8s {
  private val width = 25
  private val height = 6
  private val layerSize: Int = width * height

  def main(args: Array[String]): Unit = {
    val input = readInput
    println(s"Part A: ${checksum(input)}") // 1088
    println(s"Part B: see output") // LGYHB
    calcPixels(input)
  }

  def calcPixels(input: Array[Int]): Unit = {
    for(pixelNum <- 0 until layerSize) {
      val color = input
        .indices.collect{ case i if i % layerSize == pixelNum => input(i) } // get same pixel position for each layer
        .filter(_ < 2) // ignore transparency
        .head

      val pixel = if (color == 0) "#" else " ";

      print(s"$pixel")
      if ((pixelNum+1) % width == 0) println("|")
    }
  }

  def checksum(input: Array[Int]): Int = {
    val mostZeroes = input
      .grouped(layerSize)
      .minBy(_.count(_ == 0)) // counts zeroes

    val ones = mostZeroes.count(_ == 1)
    val twos = mostZeroes.count(_ == 2)

    ones * twos
  }

  private def readInput = {
    val bufferedSource = Source.fromFile("input/2019/day8")
    try {
      bufferedSource.getLines.next.map(_.asDigit).toArray
    } finally {
      bufferedSource.close()
    }
  }
}
