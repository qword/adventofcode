import scala.io.Source

object Day1A {
  def main(args: Array[String]): Unit = {
    var tot = 0
    Source.fromFile("input/day1").getLines.foreach(line => tot += (line toInt))
    println(tot)
  }
}

object Day1B {
  def main(args: Array[String]): Unit = {
    var tot = 0
    var set = Set[Int]()
    set += 0

    while (true) {
      Source.fromFile("input/day1").getLines.foreach(line => {
        tot += (line toInt)
        if (set contains tot) {
          println(s"found: $tot")
          System.exit(0)
        }
        set += tot
      })
    }
  }
}
