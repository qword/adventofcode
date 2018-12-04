package y2017

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day12 {

  case class Node(name: String, children: List[String], var connected: Boolean = false)
  object Node {
    def fromString(input: String): Node = {
      val tokens = input.split(" <-> ")
      Node(tokens(0), tokens(1).split(", ").toList)
    }
  }

  def main(args: Array[String]): Unit = {
    val list = ListBuffer.empty[Node]

    def checkChildren(node: Node): Unit = {
      for (childName <- node.children) {
        val child = list.find(node => node.name == childName).get
        if (!child.connected) {
          child.connected = true
          checkChildren(child)
        }
      }
    }

    Source.fromFile("input/2017/day12").getLines.foreach(line => {
      list += Node.fromString(line)
    })

    checkChildren(list.find(node => node.name == "0").get)
    println(s"Number of nodes connected to 0: ${list.count(node => node.connected)}")

    var groupsCount = 1
    while (list.count(node => node.connected) < list.size) {
      val root = list.find(node => !node.connected).get
      checkChildren(root)
      groupsCount += 1
    }
    println(s"Found $groupsCount groups")
  }
}
