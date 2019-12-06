package y2019

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day6s {

  case class Node(name: String, var parent: Node = null) {
    def depth: Int = {
      if (parent == null) 0
      else 1 + parent.depth
    }
    def path: String = {
      if (parent == null) name
      else parent.path + "-" + name
    }
  }

  def main(args: Array[String]): Unit = {

    // build a tree, sum all nodes depths
    val nodes = buildNodes()
    val checksum = calcChecksum(nodes)

    println(s"Part A: $checksum") // 314247

    val you = nodes.find(_.name == "YOU").get.path.split("-")
    val san = nodes.find(_.name == "SAN").get.path.split("-")

    var i = 0
    while (you(i) == san(i)) i = i + 1
    val distance = (you.length + san.length) - (2 * (i + 1)) // +1 because I have to avoid the terminal objects as per specs
    println(s"Part B: $distance") // 514
  }

  def calcChecksum(nodes: List[Node]): Int = {
    nodes.map(_.depth).sum
  }

  def buildNodes(): List[Node] = {
    val nodes = ListBuffer.empty[Node]

    def findNode(name: String): Node = {
      nodes.find(_.name == name).getOrElse({
        val p = Node(name)
        nodes += p
        p
      })
    }

    readInput.foreach( line => {
      val parentName = line.split("\\)")(0)
      val childName = line.split("\\)")(1)

      val parent = findNode(parentName)
      val child = findNode(childName)

      child.parent = parent
    })

    nodes.toList
  }

  def readInput: List[String] = {
    val bufferedSource = Source.fromFile("input/2019/day6")
    try {
      bufferedSource.getLines.toList
    } finally {
      bufferedSource.close()
    }
  }
}
