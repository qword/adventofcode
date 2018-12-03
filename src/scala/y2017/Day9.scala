package y2017

import scala.io.Source

object Day9 {
  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2017/day9").getLines.next()
    var garbageCount = 0

    def cleanup(input: String): String = {
      var out = ""
      var index = 0
      var inGarbage = false
      while (index < input.length) {
        if (inGarbage) {
          if (input(index) == '>') {
            inGarbage = false
          } else if (input(index) == '!') {
            index += 1
          } else {
            garbageCount += 1
          }
        } else {
          if (input(index) == '<') {
            inGarbage = true
          } else {
            out += input(index)
          }
        }
        index += 1
      }
      out
    }

    def calcScore(input: String) : Int = {
      var score = 0
      var level = 0
      for (index <- 0 until input.length) {
        if (input(index) == '{') {
          level += 1
        } else if (input(index) == '}') {
          score += level
          level -= 1
        }
      }
      score
    }

    val score = calcScore(cleanup(input))
    println(s"Score is: $score")
    println(s"Garbage count: $garbageCount")
  }
}
