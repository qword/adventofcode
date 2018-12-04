package y2018

import scala.io.Source

case class Claim(x: Int, y: Int, dimX: Int, dimY: Int, id: String)

object Claim {
  def fromString(input: String): Claim = {
    val tokens = input.split(" ")
    val position = tokens(2).split(",")
    val x = position(0).toInt
    val y = position(1).substring(0, position(1).length -1).toInt

    val dimension = tokens(3).split("x")
    val dimX = dimension(0).toInt
    val dimY = dimension(1).toInt

    Claim(x, y, dimX, dimY, tokens(0))
  }
}

object Day3 {
  def main(args: Array[String]): Unit = {
    var maxX = 0
    var maxY = 0

    // find canvas size
    Source.fromFile("input/2018/day3").getLines.foreach(line => {
      val c = Claim.fromString(line)
      if (c.x + c.dimX > maxX) maxX = c.x + c.dimX
      if (c.y + c.dimY > maxY) maxY = c.y + c.dimY
    })

    val fabric = Array.ofDim[String](maxX, maxY)
    var overlaps = 0

    // put claims on canvas
    Source.fromFile("input/2018/day3").getLines.foreach(line => {
      val c = Claim.fromString(line)

      for (a <- c.x until c.x + c.dimX) {
        for (b <- c.y until c.y + c.dimY) {
          if (fabric(a)(b) == "o") {
            overlaps += 1
            fabric(a)(b) = "e"
          }
          if (fabric(a)(b) == null) fabric(a)(b) = "o"
        }
      }
    })

    // evaluate overlaps, find not overlapping claim
    Source.fromFile("input/2018/day3").getLines.foreach(line => {
      val c = Claim.fromString(line)
      var overlapping = false

      for (a <- c.x until c.x + c.dimX) {
        for (b <- c.y until c.y + c.dimY) {
          if (fabric(a)(b) != "o") overlapping = true
        }
      }

      if (!overlapping) println(s"Not overlapping: ${c.id}")
    })

    println(s"Overlaps: $overlaps")
  }
}
