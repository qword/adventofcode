package y2019

import scala.io.Source

trait Input {
  def readSingleLineInput(path: String): Map[Long, Long] = {
    val bufferedSource = Source.fromFile(path)
    try {
      bufferedSource.getLines.next.split(",") // read sequence of numbers
        .zipWithIndex.map { case (v, i) => i.toLong -> v.toLong }.toMap // create map index -> content
    } finally {
      bufferedSource.close()
    }
  }
}
