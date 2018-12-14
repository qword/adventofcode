package y2017

import scala.io.Source

object Day16 {
  def main(args: Array[String]): Unit = {

    var arr = Array.ofDim[Char](16)
    for (i <- arr.indices) arr(i) = (i + 97).toChar

    for (i <- 1 to 100) {
      Source.fromFile("input/2017/day16").getLines.next.split(",").foreach(move => {
        if (move.startsWith("s")) spin(move)
        if (move.startsWith("x")) exchange(move)
        if (move.startsWith("p")) partner(move)
      })

      var tmp = ""
      for (i <- arr.indices) tmp += arr(i)
      if (i == 1) println(s"First round result: $tmp")
      if (i == 48) println(s"48th round result: $tmp")
      if (tmp == "abcdefghijklmnop"){
        println(s"Repetition found after $i rounds: to get to 1000000000 just repeat at the ${1000000000 % i} iteration")
        System.exit(0)
      }
    }

    def spin(str: String): Unit = {
      val p = str.substring(1).toInt
      val tmp = Array.ofDim[Char](arr.length)

      for (i <- arr.indices) tmp((p + i) % arr.length) = arr(i)
      arr = tmp
    }

    def exchange(str: String): Unit = {
      val tkns = str.substring(1).split("/")
      val posA = tkns(0).toInt
      val posB = tkns(1).toInt

      val tmp = arr(posA)
      arr(posA) = arr(posB)
      arr(posB) = tmp
    }

    def partner(str: String): Unit = {
      val tokens = str.substring(1).split("/")
      val a = tokens(0)(0)
      val b = tokens(1)(0)

      for (i <- arr.indices) {
        if (arr(i) == b) arr(i) = a
        else if (arr(i) == a) arr(i) = b
      }
    }
  }
}