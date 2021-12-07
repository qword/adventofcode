package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day6"))[0]

    val fishes = input.split(",").map{ it.toLong() }.toMutableList()

    println("Part 1 : ${calcGeneration(fishes, 80).size}")
    println("Part 2 : ${calcGenerationOptimized(fishes, 256)}")
}

private fun calcGeneration(fishes: List<Long>, limit: Long, currentGeneration: Long = 0): List<Long> {
    if (currentGeneration == limit) return fishes

    val nextGen = mutableListOf<Long>()

    fishes.forEach{ fish ->
        if (fish == 0L) {
            nextGen.add(6)
            nextGen.add(8)
        } else {
            nextGen.add(fish - 1)
        }
    }

    return calcGeneration(nextGen, limit, currentGeneration + 1)
}

private fun calcGenerationOptimized(fishes: List<Long>, limit: Long): Long {
    fun iterate(map: Map<Long, Long>, current: Long = 0): Map<Long, Long> {
        if (current == limit) return map

        val nextMap = mutableMapOf<Long, Long>()

        nextMap[0] = map[1]!!
        nextMap[1] = map[2]!!
        nextMap[2] = map[3]!!
        nextMap[3] = map[4]!!
        nextMap[4] = map[5]!!
        nextMap[5] = map[6]!!
        nextMap[6] = map[7]!! + map[0]!!
        nextMap[7] = map[8]!!
        nextMap[8] = map[0]!!

        return iterate(nextMap, current + 1)
    }

    val initialMap = mutableMapOf(0L to 0L, 1L to 0L, 2L to 0L, 3L to 0L, 4L to 0L, 5L to 0L, 6L to 0L, 7L to 0L, 8L to 0L)

    fishes.forEach { fish ->
        initialMap[fish] = initialMap[fish]!! + 1
    }

    return iterate(initialMap).values.sum()
}
