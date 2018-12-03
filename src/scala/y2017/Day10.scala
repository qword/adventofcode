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

    Source.fromFile("input/2017/day10").getLines.next().split(",").foreach(value => {
      reverse(value toInt)
    })

    println(s"Result: ${list(0) * list(1)}")
  }
}


object Day10B {
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

    for (round <- 0 until 64) {
      input.foreach(value => {
        reverse(value)
      })
    }

    var denseHash = ""
    for (a <- 0 until 16) {
      var nextChar = 0
      for (b <- 0 until 16) {
        nextChar = nextChar ^ list(b + (a * 16))
      }
      var nextHex = "%02X".format(nextChar.toHexString)
      denseHash += nextHex
    }

    println(s"Result: $denseHash")
  }
}

