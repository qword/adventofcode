package y2019

import scala.annotation.tailrec
import scala.io.Source

object Day7s {
  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 65464
  }

  def partA: String = {
    Array(0,1,2,3,4).permutations.toStream
      .map(runAmplifiers)
      .max
      .toString
  }

  def runAmplifiers(phaseSequence: Array[Int]): Int = {
    val outputA = run(readInput, Array(phaseSequence(0),0))
    val outputB = run(readInput, Array(phaseSequence(1),outputA))
    val outputC = run(readInput, Array(phaseSequence(2),outputB))
    val outputD = run(readInput, Array(phaseSequence(3),outputC))
    run(readInput, Array(phaseSequence(4),outputD))
  }

  def readInput = {
    val bufferedSource = Source.fromFile("input/2019/day7")
    try {
      bufferedSource.getLines.next.split(",").map(_.toInt)
    } finally {
      bufferedSource.close()
    }
  }

  def run(program: Array[Int], input: Array[Int]): Int = {
    var output = 0
    val inputItr = input.iterator

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
        case 3 => program(program(pointer + 1)) = inputItr.next
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
