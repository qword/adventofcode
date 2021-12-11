package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val board = Files.readAllLines(Paths.get("input/2021/day11"))
        .map{ line -> line.toCharArray().map { it.digitToInt() }.toIntArray() }
        .toTypedArray()

    val countFlashes = countFlashes(board)
    println("Part 1: ${countFlashes.first}")
    println("Part 2: ${countFlashes.second}")
}

private fun countFlashes(board: Array<IntArray>): Pair<Int, Int> {
    var flashes = 0

    for (i in 0 until Int.MAX_VALUE) {
        // increase energy level
        for (y in board.indices)
            for (x in 0 until board[y].size)
                board[y][x] = board[y][x] + 1

        // flashes
        for (y in board.indices)
            for (x in 0 until board[y].size) {

                fun flash(y: Int, x: Int) {
                    if (board[y][x] > 9) {
                        if (i < 100) flashes++
                        board[y][x] = Int.MIN_VALUE
                        for (dy in -1 .. 1)
                            for (dx in -1 .. 1)
                                if (x + dx > -1 && x + dx < board[y].size && y + dy > -1 && y + dy < board.size) {
                                    board[y + dy][x + dx] = board[y + dy][x + dx] + 1
                                    flash(y + dy, x + dx)
                                }
                    }
                }
                flash(y, x)
            }

        // cleanup
        for (y in board.indices)
            for (x in 0 until board[y].size)
                if (board[y][x] < 0) board[y][x] = 0

        // check sync flash
        if (board.flatMap { it.asIterable() }.sum() == 0) {
            return Pair(flashes, i + 1)
        }

//        show(board)
    }
    return Pair(flashes, -1)
}

private fun show(board: Array<IntArray>) {
    for (y in board.indices) {
        for (x in 0 until board[y].size) {
            val value = board[y][x].toString().padStart(2, ' ')

            if (board[y][x] == 0)
                print("\u001B[31m$value\u001B[0m")
            else
                print(value)
        }
        println()
    }
}
