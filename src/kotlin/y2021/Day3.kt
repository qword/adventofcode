package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val lines = Files.readAllLines(Paths.get("input/2021/day3"))

    part1(lines)
    part2(lines)
}

private fun part1(lines: List<String>) {
    val zeroes = IntArray(lines[0].length)

    lines.forEach{
        for (i in it.indices) {
            zeroes[i] += it[i].toString().toInt()
        }
    }

    var gamma = ""
    var epsilon = ""
    for (i in zeroes.indices) {
        if (zeroes[i] > lines.size / 2) {
            gamma += "0"
            epsilon += "1"
        } else {
            gamma += "1"
            epsilon += "0"
        }
    }

    val gammaInt = Integer.parseInt(gamma, 2)
    val epsilonInt = Integer.parseInt(epsilon, 2)

    println("Part 1: ${gammaInt * epsilonInt}")
}

private fun part2(lines: List<String>) {

    val generator = calMinMax(lines) { if (it) 1 else 0 }
    val scrubber = calMinMax(lines) { if (it) 0 else 1 }

    println("Part 2: ${generator * scrubber}")

}

private fun calMinMax(lines: List<String>, count: (boo: Boolean) -> Int): Int {
    var input = lines.map { line ->
        line.map { bit ->
            bit.toString().toInt()
        }
    }

    for (i in 0 until lines[0].length) {
        val sum = input.sumOf { it[i] }
        val leastOrMostCommon = count(sum > (input.size - 1) / 2)
        val filteredInput = input.filter { it[i] ==  leastOrMostCommon }
        input = filteredInput
        if (input.size == 1) break
    }
    var generator = ""
    for (i in input[0].indices) generator += input[0][i].toString()
    return Integer.parseInt(generator, 2)
}
