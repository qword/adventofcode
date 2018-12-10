package y2018

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Day9A {
  def main(args: Array[String]): Unit = {

    val tokens = Source.fromFile("input/2018/day9").getLines.next.split(" ")
    val maxPlayers = tokens(0).toInt
    val lastMarble = tokens(6).toInt

    var playground = ArrayBuffer.empty[Int]
    playground += 0
    var currentMarble = 1
    var currentPosition = 0
    var currentPlayer = 0
    val players = mutable.HashMap[Int, Int]()


    while (currentMarble < lastMarble) {
      if (currentMarble == 1) {
        playground += currentMarble
        currentPosition = 1
      } else if (currentMarble % 23 == 0) {

        if (players.contains(currentPlayer)) {
          players += (currentPlayer -> (players(currentPlayer) + currentMarble))
        } else {
          players += (currentPlayer -> currentMarble)
        }

        currentPosition = currentPosition - 7
        if (currentPosition < 0) {
          currentPosition = playground.size + currentPosition
        }
        players += (currentPlayer -> (players(currentPlayer) + playground(currentPosition)))
        playground.remove(currentPosition)

      } else {
        currentPosition = currentPosition + 2
        if (currentPosition == playground.size) {
          playground += currentMarble
        } else {
          if (currentPosition > playground.size) {
            currentPosition = currentPosition % playground.size
          }
          playground.insert(currentPosition, currentMarble)
        }
      }


      currentMarble += 1
      currentPlayer = (currentPlayer + 1) % maxPlayers

      if (currentMarble % 100000 == 0) {
        println(s"processed $currentMarble marbles")
      }
    }

    println(s"High score: ${players.maxBy(_._2)._2}")
  }
}


object Day9B {
  case class Node(value: Int, var prev: Node = null, var next: Node = null)

  def main(args: Array[String]): Unit = {

    val tokens = Source.fromFile("input/2018/day9").getLines.next.split(" ")
    val maxPlayers = tokens(0).toInt
    val lastMarble = tokens(6).toInt * 100

    var playgroundSize = 0
    var currentNode = Node(0)
    val root = currentNode
    playgroundSize += 1
    var currentMarble = 1
    var currentPlayer = 0
    val players = mutable.HashMap[Int, Long]()


    while (currentMarble < lastMarble) {
      if (currentMarble == 1) {
        val nextNode = Node(1)
        currentNode.next = nextNode
        currentNode.prev = nextNode

        nextNode.prev = currentNode
        nextNode.next = currentNode

        currentNode = nextNode
        playgroundSize += 1
      } else if (currentMarble % 23 == 0) {

        if (players.contains(currentPlayer)) {
          players += (currentPlayer -> (players(currentPlayer) + currentMarble))
        } else {
          players += (currentPlayer -> currentMarble)
        }

        for (i <- 0 until 7) {
          currentNode = currentNode.prev
        }

        players += (currentPlayer -> (players(currentPlayer) + currentNode.value))


        // remove current node
        currentNode.next.prev = currentNode.prev
        currentNode.prev.next = currentNode.next

        currentNode = currentNode.next
        playgroundSize -= 1
      } else {
        currentNode = currentNode.next.next

        val newNode = Node(currentMarble)

        currentNode.prev.next = newNode
        newNode.prev = currentNode.prev

        currentNode.prev = newNode
        newNode.next = currentNode

        currentNode = newNode
        playgroundSize += 1
      }

      currentMarble += 1
      currentPlayer = (currentPlayer + 1) % maxPlayers
    }

    println(s"High score: ${players.maxBy(_._2)._2}")
  }
}
