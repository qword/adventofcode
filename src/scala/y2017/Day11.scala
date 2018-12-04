package y2017

import scala.math.{abs, max}
import scala.io.Source

object Day11 {

  def maxAbs(a: Int, b: Int, c: Int): Int = {
    val result = max(abs(a), abs(b))
    max(result, abs(c))
  }

  def main(args: Array[String]): Unit = {
    // hex grid implemented with cube coordinates: https://www.redblobgames.com/grids/hexagons/#coordinates-cube
    /*

      \ n  /
    nw +--+ ne
      /    \
    -+      +-
      \    /
    sw +--+ se
      / s  \

      has 3 coordinates such as x + y + z = 0

         z
        /
   x --
        \
         y
     */

    var x = 0
    var y = 0
    var z = 0
    var maxDistance = 0

    // build path
    Source.fromFile("input/2017/day11").getLines.next().split(",").foreach(value => {
      value match {
        case "n"  => z -= 1; x += 1;
        case "nw" => z -= 1; y += 1;
        case "sw" => x -= 1; y += 1;
        case "s"  => x -= 1; z += 1;
        case "se" => y -= 1; z += 1;
        case "ne" => y -= 1; x += 1;
      }
      maxDistance = max(maxDistance, maxAbs(x, y, z))
    })

    println(s"Current distance from home: ${maxAbs(x, y, z)}")
    println(s"Max distance from home: $maxDistance")
  }
}
