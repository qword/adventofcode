package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day14 {
  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2018/day14").getLines.next.toInt
    val recipes = ListBuffer.empty[Int]
    recipes += 3
    recipes += 7

    var currentA = 0
    var currentB = 1

    while (recipes.size < input + 10) {
      val sum = recipes(currentA) + recipes(currentB)
      if (sum >= 10) recipes += (sum / 10) % 10
      recipes += sum % 10

      currentA = (currentA + recipes(currentA) + 1) % recipes.size
      currentB = (currentB + recipes(currentB) + 1) % recipes.size
    }

    print(s"Result: ")
    for (x <- recipes.length - 10 until recipes.length) print(recipes(x))
    println
  }
}

object Day14B {
  case class Node(value: Int, var prev: Node = null, var next: Node = null)

  def main(args: Array[String]): Unit = {
    val input = Source.fromFile("input/2018/day14").getLines.next

    var currentA = Node(3)
    var currentB = Node(7)
    currentA.next = currentB
    currentB.prev = currentA

    val firstNode = currentA
    var lastNode = currentB

    def addNode(value: Int): Unit = {
      val n = Node(value)
      n.prev = lastNode
      lastNode.next = n
      lastNode = n

      checkTermination()
    }

    def goForward(currentNode: Node): Node = {
      val steps = 1 + currentNode.value

      var n = currentNode
      for (x <- 0 until steps) {
        if (n == lastNode) n = firstNode
        else n = n.next
      }
      n
    }

    def checkTermination(): Unit = {
      var tmp: String = ""
      var n = lastNode
      for (x <- 0 until input.length) {
        tmp += n.value.toString
        if (n.prev != null) {
          n = n.prev
        } else {
          tmp = null
        }
      }

      if (tmp != null && tmp.reverse == input) {
        var count = 1
        while (n.prev != null) {
          count += 1
          n = n.prev
        }

        println(s"Result: $count")
        System.exit(0)
      }
    }

    while (true) {
      val sum = currentA.value + currentB.value

      if (sum >= 10) addNode((sum / 10) % 10)
      addNode(sum % 10)

      currentA = goForward(currentA)
      currentB = goForward(currentB)
    }
  }
}