package y2019

object Day1 extends Input {
  def main(args: Array[String]): Unit = {
    val input = readLines("input/2019/day1").map(_.toInt)

    val partA = input.fold(0)(_ + calcFuel(_))
    println(s"Part A: $partA") // 3297866

    val partB = input.fold(0)(_ + calcFuelComp(_))
    println(s"Part B: $partB") // 4943923
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
