package y2021

import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day13"))

    fold(input)

}

private fun fold(input: List<String>) {
    val coordinates = input.take(input.indexOfFirst { it == "" }).map { it.split(",").map { it.toInt() } }
    var board = Array(coordinates.maxOf { it[0] } + 1) { Array(coordinates.maxOf { it[1] } + 1) { "." } }

    coordinates.forEach { board[it[0]][it[1]] = "#" }

    var firstLoop = true
    input.forEach { line ->
        if (line.startsWith("fold along")) {
            val match = Regex("fold along (\\w+)=(\\d+)").find(line)!!
            val (axis, valueStr) = match.destructured
            val value = valueStr.toInt()

            if (axis == "y") {
                // fold
                board.forEach { row ->
                    for (y in value until row.size)
                        if (row[y] == "#")
                            row[value - (y - value)] = "#"
                }

                //resize

                val newBoard = Array(board.size){ Array(value) { "" } }
                for (y in 0 until board.size)
                    for (x in 0 until value)
                        newBoard[y][x] = board[y][x]
                board = newBoard

            } else {
                //fold
                for (y in value until board.size)
                    for (x in 0 until board[0].size)
                        if (board[y][x] == "#")
                            board[value - (y - value)][x] = "#"

                // resize
                val newBoard = Array(value){ Array(board[0].size) { "" } }
                for (y in 0 until value)
                    for (x in 0 until board[0].size)
                        newBoard[y][x] = board[y][x]
                board = newBoard
            }

            if (firstLoop) {
                firstLoop = false
                println("Part 1: ${board.flatten().count { it == "#" }}")
            }
        }
    }
    println("Part 2:")
    print(board)
}

private fun print(board: Array<Array<String>>) {
    for (y in 0 until board[0].size) {
        for (x in board.indices)
            print(board[x][y].padStart(2, ' '))
        println()
    }
    println()
}
