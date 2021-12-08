package y2021

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs
import kotlin.math.min


fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day7"))[0]

    val crabs = input.split(",").map{ it.toLong() }

    println("Part 1: ${calcMinDistance(crabs) { x, y -> abs(x - y) }}")
    println("Part 2: ${calcMinDistance(crabs) { x, y -> 
        val diff = abs(x - y)
        (diff * (diff + 1)) / 2
    } }")
}

private fun calcMinDistance(crabs: List<Long>, distanceCost: (x: Long, y: Long) -> Long): Long {
    val start = crabs.minOrNull()!!
    val stop = crabs.maxOrNull()!!

    fun iterate(currentPos: Long, minCost: Long): Long {
        if (currentPos > stop) return minCost

        val currentCost = crabs.sumOf { distanceCost(it, currentPos) }
        return iterate(currentPos + 1, min(minCost, currentCost))
    }

    return iterate(start, Long.MAX_VALUE)
}

