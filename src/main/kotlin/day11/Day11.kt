package day11

import common.resourceFile

fun main() {
    val day = Day11("/day11/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day11(val filename: String) {

    fun part1(): Int {
        val connections = readInput(filename)

        var from = connections["you"]!!
        val pathCount = mutableMapOf<String, Int>()
        from.forEach { pathCount[it] = 1 }

        while (from.isNotEmpty()) {
            val newFrom = mutableListOf<String>()
            for (f in from) {
                val tos = connections.getOrDefault(f, emptyList())
                tos.forEach { t ->
                    pathCount.merge(t, 1, Int::plus)
                }
                newFrom.addAll(tos)
            }
            from = newFrom
        }

        return pathCount["out"]!!
    }

    fun part2(): Long {
        val points = readInput(filename)
        return 1
    }
}

fun readInput(filename: String): Map<String, List<String>> {
    return resourceFile(filename).readLines().associate {
        val (from, tostr) = it.split(": ")
        val tos = tostr.split(" ").toList()
        Pair(from, tos)
    }
}
