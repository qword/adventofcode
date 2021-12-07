package y2021

import java.lang.Integer.max
import java.lang.Integer.min
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs


fun main() {
    val lines = Files.readAllLines(Paths.get("input/2021/day5"))

    val part1 = fillBoard(lines, false).flatMap { it.asIterable() }.count { it > 1 }
    val part2 = fillBoard(lines, true).flatMap { it.asIterable() }.count { it > 1 }

    println("Part 1: $part1")
    println("Part 2: $part2") // 11105 too low, 18869 too high, 14232 wrong
}

private fun fillBoard(lines: List<String>, isPart2: Boolean): Board {
    val board: Board = Array(1000) { IntArray(1000) { 0 } }
    lines.forEach { line ->
        val tokens = line.split("(,|->|\\s)+".toRegex())
        val x1 = tokens[0].toInt()
        val y1 = tokens[1].toInt()
        val x2 = tokens[2].toInt()
        val y2 = tokens[3].toInt()

        if (x1 == x2 || y1 == y2)
            for (y in min(y1, y2)..max(y1, y2))
                for (x in min(x1, x2)..max(x1, x2))
                    board[x][y] = board[x][y] + 1

        if (isPart2 && abs(x1 - x2) == abs(y1 - y2))
            for (i in 0..abs(x1 - x2)) {
                val addX = if (x2 > x1) i else -i
                val addY = if (y2 > y1) i else -i
                board[x1 + addX][y1 + addY] = board[x1 + addX][y1 + addY] + 1
            }
    }
    return board
}

private fun print(board: Board) {
    for (y in board.indices) {
        for (x in 0 until board[y].size)
            print(board[x][y].toString().padStart(2, ' '))
        println()
    }
    println()
}