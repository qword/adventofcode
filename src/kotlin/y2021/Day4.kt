package y2021

import java.nio.file.Files
import java.nio.file.Paths

typealias Board = Array<IntArray>

fun main() {
    val lines = Files.readAllLines(Paths.get("input/2021/day4"))
    val draw = lines[0].split(",").map { it.toInt() }
    var boards: List<Board> = getBoards(lines)
    var foundFirst = false
    var lastBoardScore = 0

    draw.forEach { number ->
        val boardsLeft = mutableListOf<Board>()
        boards.forEach { board ->
            markNumber(number, board)
            if (isBoardWinning(board)) {
                lastBoardScore = number * calcScore(board)

                if(!foundFirst) {
                    foundFirst = true
                    println("Part 1: $lastBoardScore")
                }
            } else {
                boardsLeft.add(board)
            }
        }
        boards = boardsLeft
    }

    println("Part 2: $lastBoardScore")
}

private fun getBoards(lines: List<String>): List<Board> {
    val boards: MutableList<Board> = mutableListOf()
    var board: Board = Array(5) { IntArray(5)}
    var i = 0

    lines.drop(2).forEach { line ->
        if (line.trim().isEmpty()) {
            boards.add(board)
            board = Array(5) { IntArray(5)}
            i = 0
        } else {
            board[i++] = line.split("\\s+".toRegex())
                .filter { it.isNotBlank() }
                .map{ it.toInt() }
                .toIntArray()
        }
    }
    boards.add(board)
    return boards
}

private fun isBoardWinning(board: Board): Boolean {
    var isWinning = false
    for (i in 0 until 5) {
        isWinning = isWinning || board[i].all { it == -1 } || board.map { it[i] }.all { it == -1 }
    }

    return isWinning
}

private fun markNumber(number: Int, board:Board) {
    for (x in 0 until 5)
        for (y in 0 until 5)
            if (board[x][y] == number) board[x][y] = -1
}

private fun calcScore(board: Board): Int {
    var result = 0
    for (x in 0 until 5)
        for (y in 0 until 5)
            if (board[x][y] > -1) result += board[x][y]
    return result
}