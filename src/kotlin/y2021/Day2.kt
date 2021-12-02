package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val lines = Files.readAllLines(Paths.get("input/2021/day2"))

    part1(lines)
    part2(lines)
}

fun part1(lines: List<String>) {
    var travel = 0
    var depth = 0

    lines.forEach{
        val tokens = it.split(" ")
        val action = tokens[0]
        val amount = tokens[1].toInt()

        when (action) {
            "down" -> depth += amount
            "up" -> depth -= amount
            "forward" -> travel += amount
        }
    }

    println("Part 1: ${depth * travel}")
}

fun part2(lines: List<String>) {
    var travel = 0
    var depth = 0
    var aim = 0

    lines.forEach{
        val tokens = it.split(" ")
        val action = tokens[0]
        val amount = tokens[1].toInt()

        when (action) {
            "down" -> aim += amount
            "up" -> aim -= amount
            "forward" -> {
                travel += amount
                depth += amount * aim
            }
        }
    }

    println("Part 2: ${depth * travel}")
}
