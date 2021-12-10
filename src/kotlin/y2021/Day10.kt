package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day10"))

    println("Part 1: ${calcErrors(input)}")
    println("Part 2: ${fixIncompleteLines(input)}") // 169902832 too low
}

private fun calcErrors(input: MutableList<String>) = input.sumOf { line -> getError(line) }

private fun fixIncompleteLines(input: MutableList<String>): Long {
    input.removeIf { line -> getError(line) > 0 }
    val lineScores = input.map { line ->
        val stack = ArrayDeque<Char>()
        fun iterate(position: Int = 0) {
            if (position == line.length) return

            when (val char = line[position]) {
                ')', ']', '}', '>' -> stack.removeLast() // will be ok, errors are already removed
                else -> stack.add(char)
            }

            iterate(position + 1)
        }
        iterate()

        stack.toCharArray().reversed().map { char ->
            when (char) {
                '(' -> 1L
                '[' -> 2L
                '{' -> 3L
                '<' -> 4L
                else -> 0L
            }
        }.reduce { acc, i ->
            acc * 5L + i
        }
    }

    return lineScores.sorted()[lineScores.size / 2]

}

private fun getError(line: String): Int {
    val stack = ArrayDeque<Char>()
    fun iterate(position: Int = 0): Int {
        if (position == line.length) return 0

        when (val char = line[position]) {
            ')' -> if (stack.removeLast() != '(') return 3
            ']' -> if (stack.removeLast() != '[') return 57
            '}' -> if (stack.removeLast() != '{') return 1197
            '>' -> if (stack.removeLast() != '<') return 25137
            else -> stack.add(char)
        }

        return iterate(position + 1)
    }
    return iterate()
}
