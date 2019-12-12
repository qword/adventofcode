package y2019
import scala.annotation.tailrec
import scala.io.Source

object Day11s {
  def main(args: Array[String]): Unit = {
    println(s"Part A: ${run(readInput)}") // 1133 too low
  }
  def readInput: Map[Long, Long] = {
    val bufferedSource = Source.fromFile("input/2019/day11")
    try {
      bufferedSource.getLines.next.split(",") // read sequence of numbers
        .zipWithIndex.map{case (v, i) => i.toLong -> v.toLong}.toMap // create map index -> content
    } finally {
      bufferedSource.close()
    }
  }
  def run(program: Map[Long, Long]): Int = {
    @tailrec
    def exec(
              pointer: Long,
              program: Map[Long, Long],
              hull: Map[(Int, Int), Int],
              position: (Int, Int),
              direction: (Int, Int),
              nextOutputIsColor: Boolean,
              relativeBase: Long): Int = {
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
      def getPanelColor(pos: (Int, Int)): Int = {
        hull.getOrElse(pos, 0) // default black
      }
      def setPanelColor(pos: (Int, Int), color: Int): Map[(Int, Int), Int] = {
        hull + (pos -> color)
      }
      val instruction = program(pointer) % 100
      instruction match {
        case 1 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) + getVal(2))), hull, position, direction, nextOutputIsColor, relativeBase)
        case 2 => exec(pointer + 4, program + (getIndex(3) -> (getVal(1) * getVal(2))), hull, position, direction, nextOutputIsColor, relativeBase)
        case 3 => exec(pointer + 2, program + (getIndex(1) -> getPanelColor(position)), hull, position, direction, nextOutputIsColor, relativeBase)
        case 4 =>
          val output = getVal(1).toInt
          if (nextOutputIsColor) {
            exec(pointer + 2, program, setPanelColor(position, output), position, direction, !nextOutputIsColor, relativeBase)
          } else { // movement
            val newDirection = output match {
              case 0 => direction match { // left 90 degrees
                case (0,1) => (-1,0)
                case (1,0) => (0,1)
                case (0,-1) => (1,0)
                case (-1,0) => (0,-1)
              }
              case 1 => direction match { // right 90 degrees
                case (0,1) => (1,0)
                case (1,0) => (0,-1)
                case (0,-1) => (-1,0)
                case (-1,0) => (0,1)
              }
            }
            val newPosition = (position._1 + newDirection._1, position._2 + newDirection._2)
            exec(pointer + 2, program, hull, newPosition, newDirection, !nextOutputIsColor, relativeBase)
          }
        case 5 => exec(if (getVal(1) != 0L) getVal(2) else pointer + 3, program, hull, position, direction, nextOutputIsColor, relativeBase)
        case 6 => exec(if (getVal(1) == 0L) getVal(2) else pointer + 3, program, hull, position, direction, nextOutputIsColor, relativeBase)
        case 7 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) < getVal(2)) 1 else 0)), hull, position, direction, nextOutputIsColor, relativeBase)
        case 8 => exec(pointer + 4, program + (getIndex(3) -> (if (getVal(1) == getVal(2)) 1 else 0)), hull, position, direction, nextOutputIsColor, relativeBase)
        case 9 => exec(pointer + 2, program, hull, position, direction, nextOutputIsColor, relativeBase + getVal(1))
        case 99 => hull.values.size
        case _ => -1 // actually an error but *should never happen* :)
      }
    }
    val initialHull = Map.empty[(Int, Int), Int]
    val initialPosition = (0,0)
    val initialDirection = (0,1) // up
    exec(0, program, initialHull, initialPosition, initialDirection, true, 0)
  }
}