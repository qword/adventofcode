package y2018

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day20 {
  case class Point(var x: Int, var y: Int, var dist: Int)

  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2018/day20").getLines.next
    val stack = mutable.Stack[Point]()
    var distances = ListBuffer.empty[Point]
    var prev = Point(0, 0, 0)

    for (i <- 1 until input.length) {
      val c = input(i)

      if (c == '(') {
        stack.push(prev)
      } else if (c == ')') {
        prev = stack.pop()
      } else if (c == '|') {
        prev = stack.head
      } else {
        // move
        val next = Point(prev.x, prev.y, prev.dist + 1)
        c match {
          case 'N' => next.x -= 1
          case 'S' => next.x += 1
          case 'W' => next.y -= 1
          case 'E' => next.y += 1
          case _ =>
        }

        // is there a shorter path to here?
        val tmp = distances.find(p => p.x == next.x && p.y == next.y)
        if (tmp.isDefined) {
          if (tmp.get.dist < next.dist) {
            // That's why string based solution did't work: found non-unique path
            next.dist = tmp.get.dist
          }
        } else {
          distances += next
        }

        prev = next
      }
    }

    val furthest = distances.maxBy(_.dist)
    println(s"Result part A: ${furthest.dist}")

    val thousands = distances.filter(_.dist >= 1000)
    println(s"Result part B: ${thousands.length}")
  }
}


/*

Based on the formal definition of the problem, this should be able to solve it.
But it cannot ¯\_(ツ)_/¯
It would be cool to understand why (see line 40)

object Day20 {
  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2018/day20").getLines.next

    val result = simplify(input)

    println(s"Result: ${result.length - 2} $result") // 4775 too low
  }

  def simplify(input: String): String = {
    val i = input.lastIndexOf("(")
    if (i == -1) return input

    val j = input.indexOf(")", i)

    val str = split(input.slice(i + 1, j))
    val newStr = input.slice(0, i) + str + input.substring(j + 1)

    if (newStr.contains(")")) simplify(newStr)
    else newStr
  }

  def split(str: String): String = {
    if (str.last == '|') ""
    else str.split('|').maxBy(_.length)
  }
}

*/