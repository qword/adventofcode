package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day19 {
  val reg = Array.ofDim[Int](6)
  val instructions = ListBuffer.empty[Instr]
  var pointer = -1

  case class Instr(op: String, a: Int, b: Int, c: Int)
  object Instr {
    def addr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) + reg(b)
    def addi(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) + b
    def mulr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) * reg(b)
    def muli(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) * b
    def banr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) & reg(b)
    def bani(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) & b
    def borr(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) | b
    def bori(a: Int, b: Int, c: Int): Unit = reg(c) = reg(a) | reg(b)
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
    Source.fromFile("input/2018/day19").getLines.foreach(l => {
      if (!l.isEmpty) {
        if (l.startsWith("#")) {
          pointer = l.substring(4).toInt
        } else {
          val tokens = l.split(" ")
          instructions += Instr(tokens(0), tokens(1).toInt, tokens(2).toInt, tokens(3).toInt)
        }
      }
    })

    /* Part B,
      1. found target: 10551376
      2. found divisors: 1 2 4 8 11 16 22 44 88 176 59951 119902 239804 479608 659461 959216 1318922 2637844 5275688 10551376
      3. found sum: 22302144
     */
    println(s"Solution to part 2: 22302144")

    // Part A:
    while(true) {
      cycle()
    }
  }

  def cycle(): Unit = {
    print(s"[${reg(0)}, ${reg(1)}, ${reg(2)}, ${reg(3)}, ${reg(4)}, ${reg(5)}] ")

    val nextPos = reg(pointer)
    if(nextPos >= instructions.length) {
      println
      println(s"Execution halted, result: ${reg(0)}")
      System.exit(0)
    }
    val next = instructions(nextPos)

    print(s"${next.op} ${next.a} ${next.b} ${next.c} ")

    Instr.execute(next)
    println(s"[${reg(0)}, ${reg(1)}, ${reg(2)}, ${reg(3)}, ${reg(4)}, ${reg(5)}] ")
    reg(pointer) += 1
  }
}