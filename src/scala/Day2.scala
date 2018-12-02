import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day2A {
  def main(args: Array[String]): Unit = {
    var twoLettersCount = 0
    var threeLettersCount = 0

    Source.fromFile("input/day2").getLines.foreach(line => {
      val map = line.groupBy(identity).mapValues(_.length)
      if (map.values.exists(_ == 2)) twoLettersCount += 1
      if (map.values.exists(_ == 3)) threeLettersCount += 1
    })
    println(s"Result: ${twoLettersCount * threeLettersCount}")
  }
}

object Day2B {
  def main(args: Array[String]): Unit = {
    val list = ListBuffer.empty[String]
    Source.fromFile("input/day2").getLines.foreach(line => {
      list.foreach(item => {
        if (distance(item, line) == 1) {
          print(s"Found: ")
          for (x <- 0 until item.length) {
            if (line(x) == item(x)) print(line(x))
          }
          System.exit(1)
        }
      })
      list += line
    })
  }

  def distance(s1: String, s2: String): Int = {
    var distance = 0
    for (x <- 0 until s1.length) {
      if (s1(x) != s2(x)) distance += 1
    }
    distance
  }
}
