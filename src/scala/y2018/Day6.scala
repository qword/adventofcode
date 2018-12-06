package y2018

import scala.io.Source

case class Cell(id: Int, distance: Int)

object Day6A {
  def main(args: Array[String]): Unit = {
    val maxX = 400
    val maxY = 400

    var grid = Array.ofDim[Cell](maxX, maxY) // yes, should be dynamic :)
    var currentId = 0

    Source.fromFile("input/2018/day6").getLines.foreach(line => {
      val tokens = line.split(", ")
      val cx = tokens(0).toInt
      val cy = tokens(1).toInt

      for (x <- 0 until maxX)
        for (y <- 0 until maxY) {
          val distance = Math.abs(x - cx) + Math.abs(y - cy)
          val currentCell = grid(x)(y)

          if (currentCell != null && distance == currentCell.distance) {
            grid(x)(y) = Cell(-1, distance)
          } else if (currentCell != null && distance < currentCell.distance) {
            grid(x)(y) = Cell(currentId, distance)
          } else if (currentCell == null) {
            grid(x)(y) = Cell(currentId, distance)
          }
        }

      currentId += 1
    })

    val map = scala.collection.mutable.HashMap.empty[Int, Int]

    // calculate areas
    for (x <- 0 until maxX)
      for (y <- 0 until maxY) {
        val cell = grid(x)(y)
        if (map.contains(cell.id)) {
          map += (cell.id -> (map(cell.id) + 1))
        } else {
          map += (cell.id -> 1)
        }
      }

    // remove areas that touch the edges
    for (x <- 0 until maxX) {
      map.remove(grid(x)(0).id)
      map.remove(grid(x)(maxY - 1).id)
    }
    for (y <- 0 until maxY) {
      map.remove(grid(0)(y).id)
      map.remove(grid(maxX - 1)(y).id)
    }

    println(s"Max non infinite area: ${map.maxBy(_._2)._2}")
  }
}


object Day6B {
  def main(args: Array[String]): Unit = {
    val maxX = 400
    val maxY = 400

    var answer = 0
    var grid = Array.ofDim[Cell](maxX, maxY) // yes, should be dynamic :)

    for (x <- 0 until maxX)
      for (y <- 0 until maxY) {
        var totDistance = 0

        Source.fromFile("input/2018/day6").getLines.foreach(line => {
          val tokens = line.split(", ")
          val cx = tokens(0).toInt
          val cy = tokens(1).toInt
          totDistance += Math.abs(x - cx) + Math.abs(y - cy)
        })

        if (totDistance < 10000) answer += 1
      }

    println(s"My guess would be: $answer")
  }
}
