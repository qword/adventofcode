package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class Node(
  childrenCount: Int,
  children: ListBuffer[Node] = ListBuffer.empty,
  metadata: ListBuffer[Int] = ListBuffer.empty
)

object Day8A {
  def main(args: Array[String]): Unit = {

    val input = Source.fromFile("input/2018/day8").getLines.next.split(" ").map(_.toInt)

    var index = 0
    def readNode(): Node = {
      var childrenToRead = input(index)
      index +=1
      var metadataCount = input(index)
      index += 1

      val node = Node(childrenToRead)
      while(childrenToRead > 0) {
        node.children += readNode()
        childrenToRead -= 1
      }

      while(metadataCount > 0) {
        node.metadata += input(index)
        index += 1
        metadataCount -= 1
      }
      node
    }

    val root = readNode()

    def sumMetaData(node: Node): Int = {
      var sum = node.metadata.sum
      for (child <- node.children)
        sum += sumMetaData(child)
      sum
    }

    def sumMetaDataComplex(node: Node): Int = {
      if (node.childrenCount == 0) {
        node.metadata.sum
      } else {
        var sum = 0
        for (metadata <- node.metadata) {
          if (metadata - 1 < node.children.size) {
            sum += sumMetaDataComplex(node.children(metadata - 1))
          }
        }
        sum
      }
    }

    println(s"Direct metadata sum: ${sumMetaData(root)}")
    println(s"Slightly more complex metadata sum: ${sumMetaDataComplex(root)}")
  }
}
