package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val lines = Files.readAllLines(Paths.get("input/2021/day1"))

    imperative(lines)
    functional(lines)
}

fun functional(lines: List<String>) {
    val iterator = lines.iterator()

    fun checkIncreases(previous: Int): Int {
        val current = iterator.next().toInt()
        val isIncrease = if (current > previous) 1 else 0
        return isIncrease + if (iterator.hasNext()) checkIncreases(current) else 0
    }

    println("Part 1, functional: ${checkIncreases(Int.MAX_VALUE)}")

    val anotherIterator = lines.iterator()
    fun checkIncreasesSliding(previous: Int) {

    }
}



fun imperative(lines: List<String>) {
    var count = 0
    var current = Int.MAX_VALUE

    lines.forEach{
        val next = it.toInt()
        if (next > current) {
            count++
        }
        current = next
    }

    println("Part 1, imperative: $count")

    count = 0
    val numbers = lines.map{ it.toInt() }

    var currentWindow = numbers[0] + numbers[1] + numbers[2]
    var index = 3
    do {
        val nextWindow = currentWindow - numbers[index - 3] + numbers[index]
        if (nextWindow > currentWindow) {
            count++
        }
        currentWindow = nextWindow
        index++
    } while (index < numbers.size)

    println("Part 2, imperative: $count")
}

