package y2018

import scala.collection.mutable
import scala.io.Source

object Day12 {
  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("input/2018/day12").getLines
    var state = ".........." + lines.next.substring(15) + ".................................."
    val rules = mutable.HashMap.empty[String, String]

    lines.next // skip empty second line
    while (lines.hasNext) {
      val tokens = lines.next().split(" => ")
      rules += (tokens(0) -> tokens(1))
    }

    for (x <- 0 until 20) {
      var nextState = state

      for (i <- 0 to state.length - 5) {
        var nextVal = "."
        rules.foreach(kv =>{
          if (state.substring(i, i + 5) == kv._1) {
            nextVal = kv._2
          }
        })
        nextState = replace(nextState, nextVal, i + 2)
      }
      state = nextState
      println(s"Step ${x + 1}, status: $state")
    }

    var tot = 0
    for (i <- state.indices)
      if (state(i) == '#') tot += i - 10

    println(s"Result: $tot")

  }

  def replace(str: String, c: String, pos: Int): String = {
    var tmp = ""
    if (pos > 0) tmp = str.substring(0, pos)
    tmp += c
    if (pos < str.length - 1) tmp += str.substring(pos + 1)
    tmp
  }
}

// part b was solved noticing that after the first hundred cycles or so the pattern repeats itself just translating
// right, which can be described with the formula $iteration*50+1175
