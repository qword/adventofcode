package y2019

import scala.io.Source

object Day1 {
  def main(args: Array[String]): Unit = {
    val bufferedSource = Source.fromFile("input/2019/day1")

    var acc1, acc2 = 0
    bufferedSource.getLines.foreach(line => {
      val mass = (line toInt)
      acc1 = acc1 + calcFuel(mass)
      acc2 = acc2 + calcFuelComp(mass)
    })
    bufferedSource.close()

    println(s"Part A: $acc1") // 3297866
    println(s"Part B: $acc2") // 4943923
  }

  def calcFuelComp(mass: Int): Int = {
    val fuel = calcFuel(mass)
    if (fuel > 0) fuel + calcFuelComp(fuel)
    else 0
  }

  def calcFuel(mass: Int): Int = {
    val tmp = (mass / 3) - 2
    if (tmp < 0) 0
    else tmp
  }
}
