package y2019

import scala.io.Source

object Day2s {
  val targetPartB = 19690720

  def main(args: Array[String]): Unit = {

    val program = readInput
    run(program)
    println(s"Part A: ${program(0)}") // 3297866

    val incrNoun = readInput
    incrNoun(1) = incrNoun(1) + 1
    run(incrNoun)

    val incrVerb = readInput
    incrVerb(2) = incrVerb(2) + 1
    run(incrVerb)

    val deltaNoun = incrNoun(0) - program(0)
    val deltaVerb = incrVerb(0) - program(0)

    val noun = (targetPartB - 797908) / deltaNoun
    val verb = (targetPartB - (797908 + deltaNoun * noun)) / deltaVerb

    println(s"Part B: ${noun * 100 + verb}") // 4112
  }

  def readInput = {
    val bufferedSource = Source.fromFile("input/2019/day2")
    try {
      bufferedSource.getLines.next.split(",").map(_.toInt)
    } finally {
      bufferedSource.close()
    }
  }

  def run(program: Array[Int]): Unit = {
    def exec(pointer: Int): Unit = {
      val command = program(pointer)
      if (command == 99) return

      val noun = program(pointer + 1)
      val verb = program(pointer + 2)
      val resultLocation = program(pointer + 3)
      val computation = command match {
        case 1 => program(noun) + program(verb)
        case 2 => program(noun) * program(verb)
      }
      program(resultLocation) = computation
//      println(program.mkString(","))
      exec(pointer + 4)
    }
    exec(0)
  }
}
