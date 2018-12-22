package y2018

object Day22 {

  var grid: Array[Array[Char]] = _
  var el: Array[Array[Int]] = _ // erosion level
  var depth: Int = 0
  var targetX: Int = 0
  var targetY: Int = 0

  def main(args: Array[String]): Unit = {
    depth = 4002
    targetX = 5
    targetY = 746

    build()
    display()

    println(s"Part A, total risk: ${getRisk()}")
  }

  def getRisk(): Int = {
    var tot = 0

    for (y <- 0 to targetY)
      for (x <- 0 to targetX)
        grid(x)(y) match {
          case '.' => tot += 0
          case '=' => tot += 1
          case '|' => tot += 2
          case _ =>
        }
    tot
  }

  def build(): Unit = {
    grid = Array.ofDim(targetX + 11)
    el = Array.ofDim(targetX + 11)
    for (x <- grid.indices) {
      grid(x) = Array.ofDim(targetY + 11)
      el(x) = Array.ofDim(targetY + 11)
    }

    for (y <- 0 to targetY + 10) {
      for (x <- 0 to targetX + 10) {
        var gi = 0

        if (x == 0) {
          gi = y * 48271
        } else if (y == 0) {
          gi = x * 16807
        } else {
          gi = el(x - 1)(y) * el(x)(y - 1)
        }

        el(x)(y) = (gi + depth) % 20183

        el(x)(y) % 3 match {
          case 0 => grid(x)(y) = '.'
          case 1 => grid(x)(y) = '='
          case 2 => grid(x)(y) = '|'
        }

        if (x == 0 && y == 0) {
          grid(x)(y) = 'M'
        } else if (x == targetX && y == targetY) {
          grid(x)(y) = 'T'
        }
      }
    }
  }

  def display(): Unit = {
    println
    for (y <- grid.head.indices){
      for (x <- grid.indices) print(grid(x)(y))
      println
    }
  }
}