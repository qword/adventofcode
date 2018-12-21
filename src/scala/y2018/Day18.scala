package y2018

import scala.io.Source

object Day18 {
  var grid: Array[Array[Char]] = _
  var dim: Int = 0

  def main(args: Array[String]): Unit = {
    dim = Source.fromFile("input/2018/day18").getLines.length
    grid = Array.ofDim(dim)

    var i = 0
    Source.fromFile("input/2018/day18").getLines.foreach(l => {
      grid(i) = l.toCharArray
      i += 1
    })

    for (i <- 1 until 100) {
      nextRound()
      if (i == 10) println(s"Result part A: ${count()}")
      println(s"Iteration $i -> ${count()}")
    }

    // Part B solved noting that after step 1000 the counts for each turn loops on series of 28 steps.
    // Further calculations performed in Excel
  }

  def count(): Int = {
    var trees = 0
    var lumberyard = 0

    for (x <- 0 until dim)
      for (y <- 0 until dim)
        grid(x)(y) match {
          case '|' => trees += 1
          case '#' => lumberyard += 1
          case _ =>
        }
    trees * lumberyard
  }

  def nextRound(): Unit = {
    val next: Array[Array[Char]] = Array.ofDim(dim)

    for (x <- grid.indices) {
      next(x) = Array.ofDim(dim)
      for (y <- grid.head.indices) {
        next(x)(y) = calcNext(x, y)
      }
    }
    grid = next
  }

  def calcNext(x: Int, y: Int): Char = {
    var trees = 0
    var open = 0
    var lumberyard = 0

    for (a <- -1 to 1)
      for (b <- -1 to 1)
        if (x + a > -1 && x + a < dim && y + b > -1 && y + b < dim && !(a == 0 && b == 0)) {
          grid(x + a)(y + b) match {
            case '|' => trees += 1
            case '.' => open += 1
            case '#' => lumberyard += 1
          }
        }

    if (grid(x)(y) == '.' && trees >= 3) '|'
    else if (grid(x)(y) == '|' && lumberyard >= 3) '#'
    else if (grid(x)(y) == '#') if (lumberyard > 0 && trees > 0) '#' else '.'
    else grid(x)(y)
  }

  def display(i: Int): Unit = {
    println
    println(s" ----------- Round $i -----------")
    for (x <- grid.indices) {
      for (y <- grid.head.indices)
        print(grid(x)(y))
      println
    }
  }
}