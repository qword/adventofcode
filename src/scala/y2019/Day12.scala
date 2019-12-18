package y2019
import scala.annotation.tailrec
import scala.math.{abs, signum}

case class Vector(x: Long = 0, y: Long = 0, z: Long = 0) {
  override def toString: String = f"<x=$x%3d, y=$y%3d, z=$z%3d>"
  def +(v: Vector): Vector = Vector(this.x+v.x, this.y+v.y, this.z+v.z)
  def abssum = abs(x) + abs(y) + abs(z)
}

object Day12s extends Input {

  val regex = "<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>".r

  def main(args: Array[String]): Unit = {
    val satellites = readLines("input/2019/day12")
      .map(line => parsePosition(line) -> Vector())
      .toMap

    println("Initial state")
    println(satellites.mkString("\n"))

    val steps = 1000
    val afterMove = calcPos(satellites, steps)

    println(s" --- State after $steps steps")
    println(afterMove.mkString("\n"))
    println(s"Total energy: ${energy(afterMove)}")
  }

  def parsePosition(input: String): Vector = {
    input match {
      case regex(x, y, z) => Vector(x.toLong, y.toLong, z.toLong)
    }
  }

  def calcVel(satellites: Map[Vector, Vector]): Map[Vector, Vector] = {

    @tailrec
    def updateVel(satellites: Map[Vector, Vector], couples: List[List[Vector]]): Map[Vector, Vector] = {
      if (couples.isEmpty) satellites
      else {
        val p0 = couples.head(0)
        val p1 = couples.head(1)
        val v0 = satellites(p0)
        val v1 = satellites(p1)

        val newV0 = Vector(
          x = v0.x + signum(p1.x - p0.x),
          y = v0.y + signum(p1.y - p0.y),
          z = v0.z + signum(p1.z - p0.z)
        )
        val newV1 = Vector(
          x = v1.x + signum(p0.x - p1.x),
          y = v1.y + signum(p0.y - p1.y),
          z = v1.z + signum(p0.z - p1.z)
        )

        updateVel(satellites + (p0 -> newV0, p1 -> newV1), couples.tail)
      }
    }

    updateVel(satellites, satellites.keys.toList.combinations(2).toList)
  }

  @tailrec
  def calcPos(satellites: Map[Vector, Vector], steps: Int): Map[Vector, Vector] =
    if (steps == 0) satellites
    else calcPos(calcVel(satellites).toList.map(v => (v._1 + v._2, v._2)).toMap, steps - 1)

  def energy(satellites: Map[Vector, Vector]): Long = {
    satellites.toList.map(v => v._1.abssum * v._2.abssum).sum
  }

}