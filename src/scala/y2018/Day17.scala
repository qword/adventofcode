package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day17 {
  case class Point(var x: Int, var y: Int)

  var grid: Array[Array[Char]] = _
  var round = 0
  var offX: Int = 0
  var points = ListBuffer.empty[Point]

  def main(args: Array[String]): Unit = {
    createGrid()
    populateGrid()
    display()

    points += Point(500, 0)

    while (points.nonEmpty) {
      round += 1
      addWater(points.last)
//      display()
    }

    var total = 0
    for (y <- grid.head.indices)
      for (x <- grid.indices)
        if (grid(x)(y) == '~' || grid(x)(y) == '|') total += 1

    display()
    println(s"Result part A: $total")

    var falling = 0
    for (y <- grid.head.indices)
      for (x <- grid.indices) {
        if (grid(x)(y) == '|') falling += 1
        if (grid(x)(y) == '~') {
          var falls = false

          var dx = 0
          while (grid(x - dx)(y) == '~') dx += 1
          if (grid(x - dx)(y) == '|') falls = true

          if (!falls) {
            dx = 0
            while (grid(x + dx)(y) == '~') dx += 1
            if (grid(x + dx)(y) == '|') falls = true
          }

          if (falls) falling += 1
        }
      }
    println(s"Result part B: ${total - falling} (falling: $falling)")
  }

  def addWater(p: Point) = {

    if (p.x + offX > -1 && p.x + offX < grid.length - 1) {

      for (i <- 0 until points.length - 1) {
        val po = points(i)
        if (po.x == p.x && po.y == p.y)
          points -= po
      }

      if (p.y == grid.head.length) {
        points -= p
      }

      else if (get(p.x, p.y) == '|' && p.y < grid.head.length - 1 && get(p.x, p.y + 1) == '~') {
        set(p.x, p.y, '~')
        var falls = checkLeft(p)
        falls = checkRight(p) || falls
        if (falls)
          points -= p
        else
          p.y -= 1
      }

      else if (get(p.x, p.y) == '|' || get(p.x, p.y) == '+') {
        p.y += 1
      }

      else if (get(p.x, p.y) == '.') {
        set(p.x, p.y, '|')
        p.y += 1
      }

      else if (get(p.x, p.y) == '#') {
        p.y -= 1
        set(p.x, p.y, '~')
        var falls = checkLeft(p)
        falls = checkRight(p) || falls
        if (falls)
          points -= p
        else
          p.y -= 1
      }

      else if (get(p.x, p.y) == '~') {
        var falls = false

        var dx = 0
        while (get(p.x - dx, p.y) == '~') dx += 1
        if (get(p.x - dx, p.y) == '|') falls = true

        if (falls) points -= p
        else {
          dx = 0
          while (get(p.x + dx, p.y) == '~') dx += 1
          if (get(p.x + dx, p.y) == '|') falls = true
          if (falls) points -= p
        }

        if (!falls) p.y -= 1
      }
    }
  }

  def checkLeft(p: Point): Boolean = {
    var dx = 1
    while (p.x - dx + offX > 0 && get(p.x - dx, p.y) != '#' && get(p.x - dx, p.y + 1) != '.') {
      set(p.x - dx, p.y, '~')
      dx += 1
    }
    if (get(p.x - dx, p.y + 1) == '.') {
      points += Point(p.x - dx, p.y)
      return true
    }
    false
  }

  def checkRight(p: Point): Boolean = {
    var dx = 1
    while (p.x + dx + offX <= grid.length && get(p.x + dx, p.y) != '#' && get(p.x + dx, p.y + 1) != '.') {
      set(p.x + dx, p.y, '~')
      dx += 1
    }
    if (get(p.x + dx, p.y + 1) == '.') {
      points += Point(p.x + dx, p.y)
      return true
    }
    false
  }

  def createGrid(): Unit = {

    var maxX = Int.MinValue
    var minX = Int.MaxValue
    var maxY = Int.MinValue

    Source.fromFile("input/2018/day17").getLines.foreach(l => {
      val tokens = l.split(", ")

      tokens.foreach(t => {
        val str = t.substring(t.indexOf("=") + 1)
        var max = Int.MinValue
        var min = Int.MaxValue
        if (str.contains("..")) {
          min = str.substring(0, t.indexOf("..") - 2).toInt
          max = str.substring(t.indexOf("..")).toInt
        } else {
          max = str.toInt
        }
        if (t.head == 'x') {
          if (max > maxX) maxX = max
          if (min < minX) minX = min
        } else {
          if (max > maxY) maxY = max
        }
      })
    })

    grid = Array.ofDim[Char]((maxX - minX) + 2, maxY + 1)
    offX = -minX + 1
  }

  def populateGrid(): Unit = {
    for(x <- grid.indices)
      for(y <- grid.head.indices)
        grid(x)(y) = '.'

    set(500, 0, '+')

    Source.fromFile("input/2018/day17").getLines.foreach(l => {
      var from = 0
      var to = 0
      var fixed = 0
      var fixedAxis = ' '

      val tokens = l.split(", ")
      tokens.foreach(t => {
        val str = t.substring(t.indexOf("=") + 1)
        if (str.contains("..")) {
          from = str.substring(0, t.indexOf("..") - 2).toInt
          to = str.substring(t.indexOf("..")).toInt
        } else {
          fixedAxis = t.head
          fixed = str.toInt
        }
      })

      if (fixedAxis == 'x') {
        for (y <- from to to) set(fixed, y, '#')
      } else {
        for (x <- from to to) set(x, fixed, '#')
      }
    })
  }

  def get(x: Int, y: Int): Char = grid(x + offX)(y)
  def set(x: Int, y: Int, v: Char): Unit = grid(x + offX)(y) = v
  def display(): Unit = {
    println
    println(s"--------- Round: $round ---------")
    for (y <- grid.head.indices) {
      for (x <- grid.indices)
        print(grid(x)(y))
      println
    }
  }
}