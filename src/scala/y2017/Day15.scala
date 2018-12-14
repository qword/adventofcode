package y2017

import scala.io.Source

object Day15 {
  val divisor = 2147483647

  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("input/2017/day15").getLines

    var a: Long = lines.next.substring(24).toInt
    var b: Long = lines.next.substring(24).toInt

    val factorA = 16807L
    val factorB = 48271L

    var count = 0
    for (x <- 0 to 40000000) {
      a = generate(a, factorA)
      b = generate(b, factorB)

      if ((a & 0xFFFF) == (b & 0xFFFF)) count += 1
    }

    println(s"Result: $count") // 156538 too f*****g high!
  }

  def generate(prev: Long, factor: Long): Long = {
    var tmp = (prev * factor) % divisor
    if (tmp < 0)
      tmp += divisor
    tmp
  }
}


object Day15B {
  val divisor = 2147483647L

  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("input/2017/day15").getLines

    var a: Long = lines.next.substring(24).toInt
    var b: Long = lines.next.substring(24).toInt

    val factorA = 16807L
    val factorB = 48271L

    var count = 0
    for (x <- 0 to 5000000) {

      do {
        a = generate(a, factorA)
      } while (a % 4 != 0)
      do {
        b = generate(b, factorB)
      } while (b % 8 != 0)

      if ((a & 0xFFFF) == (b & 0xFFFF)) count += 1
    }

    println(s"Result: $count") // 156538 too f*****g high!
  }

  def generate(prev: Long, factor: Long): Long = {
    var tmp = (prev * factor) % divisor
    if (tmp < 0)
      tmp += divisor
    tmp
  }
}