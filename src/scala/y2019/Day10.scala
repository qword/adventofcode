package y2019

import java.lang.Math.{atan2, pow, sqrt, PI}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object Day10s extends Input {
  case class Pos(x: Int, y: Int, cx: Int, cy: Int, var blasted: Boolean = false) {
    def dx: Int = x - cx
    def dy: Int = y - cy

    def φ: Double = (atan2(dx * 1.0, dy * -1.0) + 2 * PI) % (2 * PI) // angular coordinate φ
    def r: Double = sqrt(pow(dx, 2) + pow(dy, 2)) // radial coordinate

    override def toString: String = s"absolute: ($x, $y), from center ($dx, $dy), polar from center($φ, $r)"
  }

  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 334
    println(s"Part B: $partB") // 1119
  }

  def partA: Int = {
    val asteroids = getAsteroidsChart()
    asteroids.map(item => countVisible(asteroids, item)).max
  }

  def partB: Int = {
    val asteroids = getAsteroidsChart()
    val base = asteroids.map(item => item -> countVisible(asteroids, item)).maxBy(_._2)._1
    val asteroidsToDestroy = asteroids.filterNot(_ == base)
//    println(s"Chosen asteroid is $base")

    val sortedByAngle = asteroidsToDestroy
      .map(a => Pos(a._1, a._2, base._1, base._2))
      .groupBy(p => p.φ)
      .toSeq
      .sortBy(_._1)
      .map(_._2)

    var i = 0
    var iter = sortedByAngle.iterator

    while(true) {
      if (!iter.hasNext) iter = sortedByAngle.iterator
      val pos = iter.next
      if (pos.nonEmpty) {
        val remaining = pos.filterNot(_.blasted)
        if (remaining.nonEmpty) {
          val next = remaining.minBy(_.r)
          next.blasted = true
          i = i + 1

//          println(s"$i: vaporize ${next.x}, ${next.y}")

          if (i == 200) {
//            println(s"200th vaporization is on ${next.x}, ${next.y}") // 2819 too high 1012 too low
            return next.x * 100 + next.y
          }
        }
      }
    }

    -1
  }

  def getAsteroidsChart(): List[(Int, Int)] = {
    val lb = ListBuffer.empty[(Int, Int)]

    var y=0
    readLines("input/2019/day10").foreach(line => {
      var x=0
      line.chars.forEach(ch => {
        if (ch == '#') lb += Tuple2(x, y)
        x=x+1
      })
      y=y+1
    })

    lb.toList
  }

  def countVisible(asteroids: List[(Int, Int)], target: (Int, Int)): Int = {
    var set = Set[(Int, Int)]()

    asteroids
      .filterNot(_ == target)
      .foreach(asteroid => {
        val dx = asteroid._1 - target._1
        val dy = asteroid._2 - target._2

        val x = if (dy == 0) Integer.signum(dx) else dx / gcd(dx, dy)
        val y = if (dx == 0) Integer.signum(dy) else dy / gcd(dx, dy)

        set += Tuple2(x, y)
      })

    set.size
  }

  @tailrec
  def gcd(a: Int, b: Int): Int = if (b == 0) a.abs else gcd(b, a % b)
}
