package y2019

import scala.annotation.tailrec

object Day9s extends Input {
  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 3280416268
    println(s"Part B: $partB") // 80210
  }

  def partA: String = {
    run(readSingleLine("input/2019/day9"), Array(1)).mkString(",")
  }

  def partB: String = {
    run(readSingleLine("input/2019/day9"), Array(2)).mkString(",")
  }

  def run(program: Map[Long, Long], input: Array[Long]): List[Long] = {
    var output = List.empty[Long]
    var relativeBase = 0L
    val inputItr = input.iterator

    @tailrec
    def exec(pointer: Long, program: Map[Long, Long]): Unit = {

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
      if (instruction == 99) return

      instruction match {
        case 1 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) + getVal(2))))
        case 2 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) * getVal(2))))
        case 3 => exec(pointer + 2, program + (getIndex(1) -> inputItr.next))
        case 4 => output = output :+ getVal(1); exec(pointer + 2, program)
        case 5 => exec(if (getVal(1) != 0L) getVal(2) else pointer + 3, program)
        case 6 => exec(if (getVal(1) == 0L) getVal(2) else pointer + 3, program)
        case 7 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)))
        case 8 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)))
        case 9 => relativeBase += getVal(1); exec(pointer + 2, program)
        case _ =>
      }
    }
    exec(0, program)
    output
  }
}
