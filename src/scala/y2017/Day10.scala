package y2017

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day10 {
  def main(args: Array[String]): Unit = {
    val list = ListBuffer.empty[Int]
    for (x <- 0 to 255) list += x

    var skipSize = 0
    var currentPos = 0

    def reverse(size: Int): Unit = {
      val tmp = ListBuffer.empty[Int]
      for (x <- 0 until size) {
        tmp += list((x + currentPos) % list.length)
      }
      for (x <- 0 until size) {
        list((x + currentPos) % list.length) = tmp(size - x -1)
      }

      currentPos += (size + skipSize)
      skipSize += 1
    }

    val input = ListBuffer.empty[Int]
    val inputStr = Source.fromFile("input/2017/day10").getLines.next()
    inputStr.foreach(c => input += c)
    input += (17, 31, 73, 47, 23)

    (value => {
      reverse(value toInt)
    })

    println(s"Result: ${list(0) * list(1)}") // 26732 too high, 1898 too low
  }
}
