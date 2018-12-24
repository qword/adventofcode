package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day23 {
  case class Bot(x: Int, y: Int, z: Int, pow: Int)

  private val botRegex = """pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""".r

  def main(args: Array[String]): Unit = {

    val list = ListBuffer.empty[Bot]

    Source.fromFile("input/2018/day23").getLines.foreach {
      case botRegex(x, y, z, p) => list += Bot(x.toInt, y.toInt, z.toInt, p.toInt)
    }

    val strongest = list.maxBy(_.pow)
    val inRange = list.filter(p => isInRange(strongest, p))

    println(s"Solution part A: ${inRange.size}")
  }

  def isInRange(b1: Bot, b2: Bot): Boolean = distance(b1, b2) <= b1.pow
  def distance (b1: Bot, b2: Bot): Int = Math.abs(b1.x - b2.x) + Math.abs(b1.y - b2.y) + Math.abs(b1.z - b2.z)
}