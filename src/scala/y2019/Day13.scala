package y2019

import scala.annotation.tailrec

object Day13s extends Input {

  def main(args: Array[String]): Unit = {
    val program = readSingleLineInput("input/2019/day13")

    val partA = run(program).map(_._3).count(_==2)
    println(s"Part A: $partA") // 216

    val partB = run(program + (0L -> 2L)).last._3
    println(s"Part B: $partB") // 216

  }

  def run(program: Map[Long, Long]): List[(Int, Int, Int)] = {

    @tailrec
    def exec(
              pointer: Long,
              program: Map[Long, Long],
              output: List[(Int, Int, Int)],
              relativeBase: Long
            ): List[(Int, Int, Int)] = {

      def getMode(pos: Int): Int = {
        val inStr = program(pointer) + ""
        val posInInstr = inStr.length - (2 + pos)
        if (posInInstr > -1) inStr.charAt(posInInstr).asDigit
        else 0 // default by specs
      }

      def getIndex(pos: Int): Long =
        getMode(pos) match {
          case 0 => program(pointer + pos)
          case 1 => pointer + pos
          case 2 => program(pointer + pos) + relativeBase
        }

      def getVal(pos: Int): Long = program.getOrElse(getIndex(pos), 0)

      def calcOutput(value: Int): List[(Int, Int, Int)] = {
        if (output.nonEmpty) {
          val last = output.last
          if (last._2 == -1) output.init :+ (last._1, value, -1)
          else if (last._3 == -1) output.init :+ (last._1, last._2, value)
          else output :+ (value, -1, -1)
        } else output :+ (value, -1, -1)
      }

      def calcInput(): Long = {
        // could me more efficient if the output was trimmed...
        val cursorPos = output.filter(_._3 == 3).last._1
        val ballPos = output.filter(_._3 == 4).last._1

        if (ballPos > cursorPos) 1
        else if (ballPos < cursorPos) -1
        else 0
      }

      val instruction = program(pointer) % 100

      instruction match {
        case 1 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) + getVal(2))), output, relativeBase)
        case 2 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) * getVal(2))), output, relativeBase)
        case 3 => exec(pointer + 2, program + (getIndex(1) -> calcInput()), output, relativeBase)
        case 4 => exec(pointer + 2, program, calcOutput(getVal(1).toInt), relativeBase)
        case 5 => exec(if (getVal(1) != 0L) getVal(2) else pointer + 3, program, output, relativeBase)
        case 6 => exec(if (getVal(1) == 0L) getVal(2) else pointer + 3, program, output, relativeBase)
        case 7 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)), output, relativeBase)
        case 8 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)), output, relativeBase)
        case 9 => exec(pointer + 2, program, output, relativeBase + getVal(1))
        case 99 => output
        case _ => List.empty // actually an error but *should never happen* :)
      }
    }

    exec(0, program, List.empty, 0)
  }
}
