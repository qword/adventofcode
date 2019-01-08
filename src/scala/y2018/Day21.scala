package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day21 {
  val reg = Array.ofDim[Int](6)
  val instructions = ListBuffer.empty[Instr]
  val resultsB = ListBuffer.empty[Int]
  var pointer = -1

  case class Instr(op: String, a: Int, b: Int, c: Int)
  object Instr {
    def addr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) + reg(b)
    def addi(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) + b
    def mulr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) * reg(b)
    def muli(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) * b
    def banr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) & reg(b)
    def bani(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) & b
    def borr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) | reg(b)
    def bori(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) | b
    def setr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a)
    def seti(a: Int, b: Int, c: Int): Unit = reg(c) = a
    def gtir(a: Int, b: Int, c: Int): Unit = { if(a > reg(b)) reg(c) = 1 else reg(c) = 0 }
    def gtri(a: Int, b: Int, c: Int): Unit = { if(reg(a) > b) reg(c) = 1 else reg(c) = 0 }
    def gtrr(a: Int, b: Int, c: Int): Unit = { if(reg(a) > reg(b)) reg(c) = 1 else reg(c) = 0 }
    def eqir(a: Int, b: Int, c: Int): Unit = { if(a == reg(b)) reg(c) = 1 else reg(c) = 0 }
    def eqri(a: Int, b: Int, c: Int): Unit = { if(reg(a) == b) reg(c) = 1 else reg(c) = 0 }
    def eqrr(a: Int, b: Int, c: Int): Unit = { if(reg(a) == reg(b)) reg(c) = 1 else reg(c) = 0 }

    def execute(i: Instr): Unit ={
      i.op match {
        case "addr" => addr(i.a, i.b, i.c)
        case "addi" => addi(i.a, i.b, i.c)
        case "mulr" => mulr(i.a, i.b, i.c)
        case "muli" => muli(i.a, i.b, i.c)
        case "banr" => banr(i.a, i.b, i.c)
        case "bani" => bani(i.a, i.b, i.c)
        case "borr" => borr(i.a, i.b, i.c)
        case "bori" => bori(i.a, i.b, i.c)
        case "setr" => setr(i.a, i.b, i.c)
        case "seti" => seti(i.a, i.b, i.c)
        case "gtir" => gtir(i.a, i.b, i.c)
        case "gtri" => gtri(i.a, i.b, i.c)
        case "gtrr" => gtrr(i.a, i.b, i.c)
        case "eqir" => eqir(i.a, i.b, i.c)
        case "eqri" => eqri(i.a, i.b, i.c)
        case "eqrr" => eqrr(i.a, i.b, i.c)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    Source.fromFile("input/2018/day21").getLines.foreach(l => {
      if (!l.isEmpty) {
        if (l.startsWith("#")) {
          pointer = l.substring(4).toInt
        } else {
          val tokens = l.split(" ")
          instructions += Instr(tokens(0), tokens(1).toInt, tokens(2).toInt, tokens(3).toInt)
        }
      }
    })

    // Part A:
    while(cycleUntilTerminationA()) {}

    // Part B:
    while(cycleUntilTerminationB()) {}
  }

  def cycleUntilTerminationA(): Boolean = {
    val nextPos = reg(pointer)
    val next = instructions(nextPos)

    Instr.execute(next)
    reg(pointer) += 1

    if (next.op == "eqrr") {
      println(s"\nSolution to part A: ${reg(4)}")
      return false
    }
    true
  }

  def cycleUntilTerminationB(): Boolean = {
    val nextPos = reg(pointer)
    val next = instructions(nextPos)

    Instr.execute(next)

    if (next.op == "eqrr"){
      if (resultsB.contains(reg(4))) {
        // hit repetition, from here on an endless loop will ensue. Correct answer was last added on resultsB
        println(s"Result to part B is ${resultsB.last}") // 7717135
        return false
      } else {
        resultsB += reg(4)
      }
    }

    reg(pointer) += 1
    true
  }
}