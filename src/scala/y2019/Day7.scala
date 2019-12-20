package y2019

import scala.annotation.tailrec

object Day7s extends Input {
  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 65464
  }

  def partA: String = {
    val program = readSingleLine("input/2019/day7")
    (0 to 4).permutations
      .map(seq => seq.foldLeft(0L)((acc, i) => exec(program, input = List(i, acc)).head))
      .max
      .toString
  }

  @tailrec
  def exec(
            program: Map[Long, Long],
            pointer: Long = 0,
            input: List[Long] = List.empty,
            output: List[Long] = List.empty
          ): List[Long] = {

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
      }

    def getVal(pos: Int): Long = program.getOrElse(getIndex(pos), 0)

    val instruction = program(pointer) % 100

    instruction match {
      case 1 => exec(program + (getIndex(3) -> (getVal(1) + getVal(2))), pointer + 4, input, output)
      case 2 => exec(program + (getIndex(3) -> (getVal(1) * getVal(2))), pointer + 4, input, output)
      case 3 => exec(program + (getIndex(1) -> input.head), pointer + 2, input.tail, output)
      case 4 => exec(program, pointer + 2, input, output :+ getVal(1))
      case 5 => exec(program, if (getVal(1) != 0L) getVal(2) else pointer + 3, input, output)
      case 6 => exec(program, if (getVal(1) == 0L) getVal(2) else pointer + 3, input, output)
      case 7 => exec(program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)), pointer + 4, input, output)
      case 8 => exec(program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)), pointer + 4, input, output)
      case 99 => output
      case _ => List.empty // actually an error but *should never happen* :)
    }
  }
}
