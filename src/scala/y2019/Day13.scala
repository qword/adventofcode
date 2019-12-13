package y2019

import scala.annotation.tailrec

object Day13s extends Input {

  def main(args: Array[String]): Unit = {
    val program = readSingleLineInput("input/2019/day13")
    val output = run(program)

    val partA = output.zipWithIndex.
      collect {case (e,i) if (i % 3) == 2 => e}
      .count(_==2)
    println(s"Part A: $partA") // 216

  }

  def run(program: Map[Long, Long]): List[Int] = {

    @tailrec
    def exec(
              pointer: Long,
              program: Map[Long, Long],
              output: List[Int],
              relativeBase: Long
            ): List[Int] = {

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

      val instruction = program(pointer) % 100

      instruction match {
        case 1 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) + getVal(2))), output, relativeBase)
        case 2 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) * getVal(2))), output, relativeBase)
        case 3 => exec(pointer + 2, program + (getIndex(1) -> 1), output, relativeBase) // no actual input needed for today
        case 4 => exec(pointer + 2, program, output :+ getVal(1).toInt, relativeBase)
        case 5 => exec(if (getVal(1) != 0L) getVal(2) else pointer + 3, program, output, relativeBase)
        case 6 => exec(if (getVal(1) == 0L) getVal(2) else pointer + 3, program, output, relativeBase)
        case 7 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)), output, relativeBase)
        case 8 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)), output, relativeBase)
        case 9 => exec(pointer + 2, program, output, relativeBase + getVal(1))
        case 99 => output
        case _ => List.empty // actually an error but *should never happen* :)
      }
    }

    exec(0, program, List.empty[Int], 0)
  }
}
