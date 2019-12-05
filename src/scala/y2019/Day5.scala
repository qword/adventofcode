package y2019
import scala.annotation.tailrec
import scala.io.Source

object Day5s {

  def main(args: Array[String]): Unit = {

    val partA = run(readInput, 1)
    println(s"Part A: $partA") // 4887191

    val partB = run(readInput, 5)
    println(s"Part B: $partB") // 3419022
  }

  def readInput = {
    val bufferedSource = Source.fromFile("input/2019/day5")
    try {
      bufferedSource.getLines.next.split(",").map(_.toInt)
    } finally {
      bufferedSource.close()
    }
  }

  def run(program: Array[Int], input: Int): Int = {
    var output = 0

    @tailrec
    def exec(pointer: Int): Unit = {
      val instruction = program(pointer) % 100
      if (instruction == 99) return

      def getMode(pos: Int): Int = {
        val inStr = program(pointer) + ""
        val posInInstr = inStr.length - (2 + pos)
        if (posInInstr > -1) inStr.charAt(posInInstr).asDigit
        else 0 // default by specs
      }

      def getParamValue(pos: Int): Int = {
        getMode(pos) match {
          case 0 => program(program(pointer + pos))
          case 1 => program(pointer + pos)
        }
      }

      instruction match {
        case 1 => program(program(pointer + 3)) = getParamValue(1) + getParamValue(2)
        case 2 => program(program(pointer + 3)) = getParamValue(1) * getParamValue(2)
        case 3 => program(program(pointer + 1)) = input
        case 4 => output = getParamValue(1)
        case 7 => program(program(pointer + 3)) = if (getParamValue(1) < getParamValue(2)) 1 else 0
        case 8 => program(program(pointer + 3)) = if (getParamValue(1) == getParamValue(2)) 1 else 0
        case _ =>
      }

      val nextPointer = instruction match {
        case 1 | 2 | 7 | 8 => pointer + 4
        case 3 | 4 => pointer + 2
        case 5 => if (getParamValue(1) != 0) getParamValue(2) else pointer + 3
        case 6 => if (getParamValue(1) == 0) getParamValue(2) else pointer + 3
      }
      exec(nextPointer)
    }

    exec(0)
    output
  }
}