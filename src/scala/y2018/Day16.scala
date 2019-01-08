package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day16 {
  val reg = Array.ofDim[Int](4)
  val regInitial = Array.ofDim[Int](4)
  val result = Array.ofDim[Int](4)

  var map = Map.empty[Int, String]


  def main(args: Array[String]): Unit = {
    var count = 0

    var a = -1
    var b = -1
    var c = -1
    var opcode = -1

    Source.fromFile("input/2018/day16").getLines.foreach(l => {

      if (!l.isEmpty) {

        if (l.startsWith("Before:")) {
          val before = l.substring(9, l.length - 1)
          val tokens = before.split(", ")
          tokens.indices.foreach(i => regInitial(i) = tokens(i).toInt)
        } else if (l.startsWith("After:")) {
          val after = l.substring(9, l.length - 1)
          val tokens = after.split(", ")
          tokens.indices.foreach(i => result(i) = tokens(i).toInt)

          simulate()

        } else {
          val tokens = l.split(" ")
          opcode = tokens(0).toInt
          a = tokens(1).toInt
          b = tokens(2).toInt
          c = tokens(3).toInt
        }
      }
    })

    println(s"Solution to part one is: $count")

    def simulate(): Unit = {
      var matches = ListBuffer.empty[String]
      // TODO: I guess it can be done more... functionally?
      reg.indices.foreach(i => reg(i) = regInitial(i)); addr(a, b, c); if (reg.sameElements(result)) matches += "addr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); addi(a, b, c); if (reg.sameElements(result)) matches += "addi"
      reg.indices.foreach(i => reg(i) = regInitial(i)); mulr(a, b, c); if (reg.sameElements(result)) matches += "mulr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); muli(a, b, c); if (reg.sameElements(result)) matches += "muli"
      reg.indices.foreach(i => reg(i) = regInitial(i)); banr(a, b, c); if (reg.sameElements(result)) matches += "banr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); bani(a, b, c); if (reg.sameElements(result)) matches += "bani"
      reg.indices.foreach(i => reg(i) = regInitial(i)); borr(a, b, c); if (reg.sameElements(result)) matches += "borr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); bori(a, b, c); if (reg.sameElements(result)) matches += "bori"
      reg.indices.foreach(i => reg(i) = regInitial(i)); setr(a, b, c); if (reg.sameElements(result)) matches += "setr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); seti(a, b, c); if (reg.sameElements(result)) matches += "seti"
      reg.indices.foreach(i => reg(i) = regInitial(i)); gtir(a, b, c); if (reg.sameElements(result)) matches += "gtir"
      reg.indices.foreach(i => reg(i) = regInitial(i)); gtri(a, b, c); if (reg.sameElements(result)) matches += "gtri"
      reg.indices.foreach(i => reg(i) = regInitial(i)); gtrr(a, b, c); if (reg.sameElements(result)) matches += "gtrr"
      reg.indices.foreach(i => reg(i) = regInitial(i)); eqir(a, b, c); if (reg.sameElements(result)) matches += "eqir"
      reg.indices.foreach(i => reg(i) = regInitial(i)); eqri(a, b, c); if (reg.sameElements(result)) matches += "eqri"
      reg.indices.foreach(i => reg(i) = regInitial(i)); eqrr(a, b, c); if (reg.sameElements(result)) matches += "eqrr"

      if (matches.size >= 3) count += 1
      //      print(s"opcode $opcode -> instuction ")
      //      matches.foreach(m => print(s"$m, "))
      //      println
    }

    // second part
    reg.indices.foreach(i => reg(i) = 0)
    prepareMap()

    var i = 0
    Source.fromFile("input/2018/day16").getLines.foreach(l => {
      i += 1
      if (i > 3122) {
        val tokens = l.split(" ")
        val a = tokens(1).toInt
        val b = tokens(2).toInt
        val c = tokens(3).toInt
        map(tokens(0).toInt) match {
          case "addr" => addr(a, b, c)
          case "addi" => addi(a, b, c)
          case "mulr" => mulr(a, b, c)
          case "muli" => muli(a, b, c)
          case "banr" => banr(a, b, c)
          case "bani" => bani(a, b, c)
          case "borr" => borr(a, b, c)
          case "bori" => bori(a, b, c)
          case "setr" => setr(a, b, c)
          case "seti" => seti(a, b, c)
          case "gtir" => gtir(a, b, c)
          case "gtri" => gtri(a, b, c)
          case "gtrr" => gtrr(a, b, c)
          case "eqir" => eqir(a, b, c)
          case "eqri" => eqri(a, b, c)
          case "eqrr" => eqrr(a, b, c)
        }
        println(s"$l -> ${reg(0)}, ${reg(1)}, ${reg(2)}, ${reg(3)}")
      }
    })
    println(s"Solution to second part: ${reg(0)}")
  }

  // TODO: I guess it can be done more... functionally?
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


  def prepareMap(): Unit = {
    // TODO : should be able to do it automatically...
    map += (15 -> "addr")
    map += (14 -> "eqir")
    map += (13 -> "bori")
    map += (12 -> "addi")
    map += (11 -> "eqri")
    map += (10 -> "gtri")
    map += (9 -> "seti")
    map += (8 -> "gtrr")
    map += (7 -> "mulr")
    map += (6 -> "gtir")
    map += (5 -> "eqrr")
    map += (4 -> "borr")
    map += (3 -> "setr")
    map += (2 -> "muli")
    map += (1 -> "banr")
    map += (0 -> "bani")
  }
}