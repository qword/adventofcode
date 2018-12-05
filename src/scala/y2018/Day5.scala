package y2018

import scala.io.Source

object Day5 {

  def collapse(input: String) : String = {
    var index = 0
    var str = input
    while(index < str.length - 1) {
      val s1 = str(index) toString
      val s2 = str(index + 1) toString

      if (s1.equalsIgnoreCase(s2) && !s1.equals(s2)) {
        str = str.substring(0, index) + str.substring(index + 2)
        if (index > 0) index -= 1
      }
      else index += 1
    }
    str
  }

  def main(args: Array[String]): Unit = {

    var minPolymerLength = Int.MaxValue
    val input = Source.fromFile("input/2018/day5").getLines next

    // Find all symbols
    var set = Set[String]()
    input.foreach(char => {
      set += char.toString.toLowerCase
    })

    // find collapsed lengths when removing a symbol
    for (symbol <- set) {
      var iteration = ""
      input.foreach(char => {
        if (!char.toString.equalsIgnoreCase(symbol)) {
          iteration += char
        }
      })

      val collapsed = collapse(iteration)
      if (collapsed.length < minPolymerLength) minPolymerLength = collapsed length
    }

    println(s"Result part A: ${collapse(input) length}")
    println(s"Result part B: $minPolymerLength")
  }
}
