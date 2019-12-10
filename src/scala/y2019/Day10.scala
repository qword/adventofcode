package y2019

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day10s {
  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 334
  }

  def partA: Int = {
    val asteroids = readInput()
    asteroids.map(item => countVisible(asteroids, item)).max
  }

  // sucks big time, needs to be rewritten in proper scala
  def readInput(): List[(Int, Int)] = {
    val lb = ListBuffer.empty[(Int, Int)]
    val bufferedSource = Source.fromFile("input/2019/day10")

    try {
      var y=0
      bufferedSource.getLines.foreach(line => {
        var x=0
        line.chars.forEach(ch => {
          if (ch == '#') lb += Tuple2(x, y)
          x=x+1
        })
        y=y+1
      })
      lb.toList
    } finally {
      bufferedSource.close()
    }
  }

  // sucks big time, needs to be rewritten in proper scala
  def countVisible(asteroids: List[(Int, Int)], target: (Int, Int)): Int = {
    var set = Set[(Int, Int)]()

    asteroids.filter(item => item._1 != target._1 || item._2 != target._2).foreach(asteroid => {
      val dx = asteroid._1 - target._1
      val dy = asteroid._2 - target._2

      val x = if (dy == 0) Integer.signum(dx) else dx / gcd(dx, dy)
      val y = if (dx == 0) Integer.signum(dy) else dy / gcd(dx, dy)

      set += Tuple2(x, y)
    })

    set.size
  }

  def gcd(a: Int, b: Int): Int = if (b == 0) a.abs else gcd(b, a % b)
}
