package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class Point(var x:Int, var y:Int, vx: Int, vy: Int)

object Day10 {
  def main(args: Array[String]): Unit = {

    val points  = ListBuffer.empty[Point]

    def display() = {
      val maxX = points.maxBy(_.x).x
      val minX = points.minBy(_.x).x
      val maxY = points.maxBy(_.y).y
      val minY = points.minBy(_.y).y

      val display = Array.ofDim[String](maxX - minX + 1, maxY - minY + 1)
      for (x <- 0 to maxX - minX)
        for (y <- 0 to maxY - minY) {
          display(x)(y)=" "
        }

      points.foreach(p => {
        display(p.x - minX)(p.y - minY) = "#"
      })

      println()
      for (x <- 0 to maxX - minX) {
        for (y <- 0 to maxY - minY) {
          print(display(x)(y))
        }
        println()
      }
    }

    Source.fromFile("input/2018/day10").getLines.foreach(line => {
      val x = line.substring(10, 16).trim.toInt
      val y = line.substring(18, 24).trim.toInt * -1
      val vx = line.substring(36, 38).trim.toInt
      val vy = line.substring(40, 42).trim.toInt * -1

      points += Point(x, y, vx, vy)
    })

    var dx = Int.MaxValue
    var dy = Int.MaxValue

    for (i <- 0 to 20000) {
      // move points
      points.foreach(p => {
        p.x += p.vx
        p.y += p.vy
      })

      val maxX = points.maxBy(_.x).x
      val minX = points.minBy(_.x).x
      val maxY = points.maxBy(_.y).y
      val minY = points.minBy(_.y).y

      if (Math.abs(maxX - minX) < dx && Math.abs(maxY - minY) < dy) {
        dx = Math.abs(maxX - minX)
        dy = Math.abs(maxY - minY)
      } else {
        println(s"Minimum dot dispersion, look around iteration $i")
        System.exit(0)
      }

      if (i == 10604) { // checked manually :)
        println(s"${i + 1} seconds has passed, current positions:")
        display()
        println("-------------------------------------------------")
      }
    }
  }
}
