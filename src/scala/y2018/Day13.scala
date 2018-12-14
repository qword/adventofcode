package y2018

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Day13 {

  case class Cart(var x: Int, var y: Int, var direction: Char, var whenCross: Char = 'l')

  def main(args: Array[String]): Unit = {

    val grid = Array.ofDim[Char](152, 152)
    val carts = ListBuffer.empty[Cart]

    var x = 0
    Source.fromFile("input/2018/day13").getLines.foreach(line => {
      for (y <- line.indices) {
        var c: Char = line(y)
        c match {
          case '^' => carts += Cart(x, y, c); c = '|'
          case 'v' => carts += Cart(x, y, c); c = '|'
          case '<' => carts += Cart(x, y, c); c = '-'
          case '>' => carts += Cart(x, y, c); c = '-'
          case _ =>
        }

        grid(x)(y) = c
      }
      x += 1
    })

    println(s"There are ${carts.size} carts")

    // move carts
    var step = 0
    while (true) {
      step += 1
      carts.sortBy(c => (c.y, c.x)).foreach(c => {

        c.direction match {
          case '^' => c.x -= 1
          case 'v' => c.x += 1
          case '<' => c.y -= 1
          case '>' => c.y += 1
          case _ =>
        }

        grid(c.x)(c.y) match {
          case '/' => turn1(c)
          case '\\' => turn2(c)
          case '+' => turnX(c)
          case _ =>
        }

        // perform collision control on moved carts
        val map = mutable.HashMap.empty[String, Cart]

        carts.foreach(c => {
          val str = s"${c.y},${c.x}"
          if (map.keySet.contains(str)) {
            println(s"Collision at $str during step $step")
            carts -= c
            carts -= map(str)
          }
          map += (str -> c)
        })

        // check if it's last turn
        if (carts.size == 1) {
          val lastCart = carts.head
          println(s"Last cart is at ${lastCart.y},${lastCart.x}, direction ${lastCart.direction}")
          System.exit(0)
        }
      })
    }
  }

  def turn1(cart: Cart) = {
    cart.direction match {
      case '^' => cart.direction = '>'
      case 'v' => cart.direction = '<'
      case '<' => cart.direction = 'v'
      case '>' => cart.direction = '^'
    }
  }

  def turn2(cart: Cart) = {
    cart.direction match {
      case '^' => cart.direction = '<'
      case 'v' => cart.direction = '>'
      case '<' => cart.direction = '^'
      case '>' => cart.direction = 'v'
    }
  }

  def turnX(cart: Cart) = {
    cart.whenCross match {
      case 'l' => cart.whenCross = 's'; turnL(cart)
      case 's' => cart.whenCross = 'r'
      case 'r' => cart.whenCross = 'l'; turnR(cart)
    }
  }

  def turnL(cart: Cart) = {
    cart.direction match {
      case '^' => cart.direction = '<'
      case 'v' => cart.direction = '>'
      case '<' => cart.direction = 'v'
      case '>' => cart.direction = '^'
    }
  }

  def turnR(cart: Cart) = {
    cart.direction match {
      case '^' => cart.direction = '>'
      case 'v' => cart.direction = '<'
      case '<' => cart.direction = '^'
      case '>' => cart.direction = 'v'
    }
  }
}