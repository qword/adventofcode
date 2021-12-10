package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val board = Files.readAllLines(Paths.get("input/2021/day9"))
        .map{ line -> line.toCharArray().map { it.digitToInt() }.toIntArray() }
        .toTypedArray()

    val lowPointsCount = calcLowPoints(board).first
    val basinsCalc = calcLowPoints(board).second

    println("Part 1: $lowPointsCount")
    println("Part 2: $basinsCalc")
}

private fun calcLowPoints(board: Array<IntArray>): Pair<Int, Int> {
    var count = 0
    val basins = mutableListOf<Int>()

    for (y in board.indices)
        for (x in 0 until board[y].size) {
            val currentVal = board[y][x]
            if ((x == board[y].size - 1 || currentVal < board[y][x + 1])
                && (x == 0 || currentVal < board[y][x - 1])
                && (y == board.size - 1 || currentVal < board[y+1][x])
                && (y == 0 || currentVal < board[y-1][x])
            ) {
                basins.add(calcBasinSize(board, y, x))
                count += currentVal + 1
            }
        }

    val basinValue = basins.sorted().drop(basins.size - 3).reduce { acc, i ->  acc * i}

    return Pair(count, basinValue)
}

private fun calcBasinSize(board: Array<IntArray>, y: Int, x: Int): Int {
    val visitedBoard = Array(board.size) { IntArray(board[0].size) { 0 } }

    fun visit(y: Int, x: Int): Unit {
        if (visitedBoard[y][x] == 1 || board[y][x] == 9) return
        visitedBoard[y][x] = 1

        if (x > 0) visit(y, x-1)
        if (x < board[y].size - 1) visit(y, x+1)
        if (y > 0) visit(y-1, x)
        if (y < board.size - 1) visit(y+1, x)
    }

    visit(y, x)

    return visitedBoard.flatMap { it.asIterable() }.sum()
}
