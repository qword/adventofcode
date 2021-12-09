package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day8"))

    val results = input.map{ it.split("|")[1].split(" ") }.flatten()
    val specialTokens = results.count { it.length in setOf(2, 3, 4, 7) }

    println("Part 1: $specialTokens")
    println("Part 2: ${parse(input)}")
}

private fun parse(input: List<String>): Int {
    var total = 0
    input.forEach{ line ->
        val tokens = line.split("(\\s|\\|)+".toRegex()).map{ it.toCharArray().toSet() }.toMutableSet()

        val one = tokens.firstOrNull { it.size == 2 }
        val seven = tokens.firstOrNull { it.size == 3 }
        val four = tokens.firstOrNull { it.size == 4 }
        val eight = tokens.firstOrNull { it.size == 7 }

        val nine = tokens.firstOrNull { it.size == 6 && four != null &&it.containsAll(four) }
        tokens.remove(nine)
        val zero = tokens.firstOrNull { it.size == 6 && one != null && it.containsAll(one) }
        tokens.remove(zero)
        val six = tokens.firstOrNull { it.size == 6 && nine != null && !it.containsAll(nine) && zero != null && !it.containsAll(zero)}

        val three = tokens.firstOrNull { it.size == 5 && one != null && it.containsAll(one) }
        tokens.remove(three)
        val five = tokens.firstOrNull { it.size == 5 && six != null && six.containsAll(it) }
        tokens.remove(five)
        val two = tokens.firstOrNull { it.size == 5 && three != null && !it.containsAll(three) && five != null && !it.containsAll(five) }

        val results = line.split("| ")[1].split(" ").map{ it.toCharArray().toSet() }

        var result = ""
        results.forEach{
            val digit = when(it) {
                one -> 1
                two -> 2
                three -> 3
                four -> 4
                five-> 5
                six -> 6
                seven -> 7
                eight -> 8
                nine-> 9
                zero -> 0
                else -> -1
            }
            result += digit.toString()
        }
        total += result.toInt()
    }

    return total
}
