package y2019

import scala.annotation.tailrec

object Day7s  {
  def main(args: Array[String]): Unit = {
    println(s"Part A: $partA") // 65464
  }

  def partA: String = {
    (0 to 4).permutations
      .map(seq => seq.foldLeft(0L)((acc, i) => Ampli.runOnce(Ampli.init, List(i, acc))))
      .max
      .toString
  }

  object Ampli extends Input {
    type Program = Map[Long, Long]

    case class State(program: Program,
                     pointer: Long = 0,
                     input: List[Long] = List.empty,
                     output: List[Long] = List.empty
                    )

    def init: Program = readSingleLine("input/2019/day7")

    def runOnce(program: Program, initialInput: List[Long]): Long =
      exec(State(program, input = initialInput)).head

    @tailrec
    def exec(state: State): List[Long] = {
      val program = state.program
      val pointer = state.pointer
      val input = state.input
      val output = state.output

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

      val nextState = instruction match {
        case 1 => State(program + (getIndex(3) -> (getVal(1) + getVal(2))), pointer + 4, input, output)
        case 2 => State(program + (getIndex(3) -> (getVal(1) * getVal(2))), pointer + 4, input, output)
        case 3 => State(program + (getIndex(1) -> input.head), pointer + 2, input.tail, output)
        case 4 => State(program, pointer + 2, input, output :+ getVal(1))
        case 5 => State(program, if (getVal(1) != 0L) getVal(2) else pointer + 3, input, output)
        case 6 => State(program, if (getVal(1) == 0L) getVal(2) else pointer + 3, input, output)
        case 7 => State(program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)), pointer + 4, input, output)
        case 8 => State(program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)), pointer + 4, input, output)
        case 99 => State(program, pointer = -1)
        case _ => State(program, pointer = -2) // error
      }

      nextState.pointer match {
        case -1 | -2 => output // resolve error gracefully in this case
        case _ => exec(nextState)
      }
    }
  }
}
