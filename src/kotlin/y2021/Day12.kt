package y2021

import java.nio.file.Files
import java.nio.file.Paths

data class Cave(val name: String, val children: MutableList<Cave> = mutableListOf(), val parents: MutableList<Cave> = mutableListOf()) {
    fun isSmall() = name.all { it.isLowerCase() }
    override fun toString(): String {
        return "$name -> [${children.joinToString(",") { it.name }}]"
    }
}

fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day12"))

    println("Part 1: ${simpleVisit(buildCaves(input))}")
    println("Part 2: ${visitOneSmallCaveTwice(buildCaves(input))}")
}

private fun buildCaves(input: MutableList<String>): Cave {
    val map = mutableMapOf<String, Cave>()

    input.forEach { line ->
        val tokens = line.split("-")
        val from = map.getOrPut(tokens[0]) { Cave(tokens[0]) }
        val to = map.getOrPut(tokens[1]) { Cave(tokens[1]) }
        from.children.add(to)
        to.parents.add(from)
    }

    return map["start"]!!
}

private fun simpleVisit(cave: Cave, path: String = ""): Int {
    if (cave.name == "end") {
//        println(path + "end")
        return 1
    }
    val currentPath = path + cave.name + ","

    val reachableCaves = mutableListOf<Cave>()
    reachableCaves.addAll(cave.children)
    reachableCaves.addAll(cave.parents)

    return reachableCaves.sumOf { nextCave ->
        if (!nextCave.isSmall() || !currentPath.contains(nextCave.name))
            simpleVisit(nextCave, currentPath)
        else 0
    }
}

private fun visitOneSmallCaveTwice(cave: Cave, path: MutableList<String> = mutableListOf()): Int {
    if (cave.name == "end") {
//        println(path + "end")
        return 1
    }
    val currentPath = mutableListOf<String>()
    currentPath.addAll(path)
    currentPath.add(cave.name)

    val reachableCaves = mutableListOf<Cave>()
    reachableCaves.addAll(cave.children)
    reachableCaves.addAll(cave.parents)

    return reachableCaves.sumOf { nextCave ->

        fun canEnterSmallCave(input: List<String>, next: String): Boolean {
            val map = (input + next).groupingBy { it }.eachCount()
            if (map.values.any { it > 2 }) return false // will we visit a cave 3 times?
            return map.filterValues { it > 1 }.count() < 2 // will we visit two caves twice?
        }

        val smallCavesSoFar = currentPath.filter { it.all { it.isLowerCase() } }
        val canEnterSmallCave = canEnterSmallCave(smallCavesSoFar, nextCave.name)

        if (nextCave.name != "start" && (!nextCave.isSmall() || canEnterSmallCave))
            visitOneSmallCaveTwice(nextCave, currentPath)
        else 0
    }
}
