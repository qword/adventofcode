package y2021

import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    val input = Files.readAllLines(Paths.get("input/2021/day14"))
    val template = input.first()

    val rules = input.drop(2).associate { line ->
        val match = Regex("(\\w+) -> (\\w+)").find(line)!!
        val (pair, replacement) = match.destructured
        pair to replacement
    }


    println("Part 1: ${count(template, rules, 10)}")
//    println("Part 1: ${count(template, rules, 40)}") // too slow :(
}

private fun count(template: String, rules: Map<String, String>, maxIterations: Int): Int {
    val resultTemplate = replace(template, rules, maxIterations)
    val counts = resultTemplate.groupingBy { it }.eachCount()
    val mostCommon = counts.values.maxOrNull()
    val leastCommon = counts.values.minOrNull()
    return mostCommon!! - leastCommon!!
}

private fun replace(template: String, rules: Map<String, String>, maxIterations: Int, currentIteration: Int = 0): String {
    if (currentIteration == maxIterations) return template

    val stringBuilder = StringBuilder()
    for(pos in 0 until template.length - 1) {
        val pair = template.substring(pos, pos + 2)

        stringBuilder.append(template[pos])
        if (rules.containsKey(pair))
            stringBuilder.append(rules[pair])
    }
    val resultingTemplate = stringBuilder.append(template.last()).toString()
    return replace(resultingTemplate, rules, maxIterations, currentIteration + 1)
}

private fun replaceDepthFirst(template: String, rules: Map<String, String>, maxIterations: Int, currentIteration: Int = 0): Map<String, Int> {


    val stringBuilder = StringBuilder()
    for(pos in 0 until template.length - 1) {
        val pair = template.substring(pos, pos + 2)

        stringBuilder.append(template[pos])
        if (rules.containsKey(pair))
            stringBuilder.append(rules[pair])
    }
    val resultingTemplate = stringBuilder.append(template.last()).toString()
    return replace(resultingTemplate, rules, maxIterations, currentIteration + 1)
}