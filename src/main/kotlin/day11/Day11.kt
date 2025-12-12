package day11

import common.resourceFile
import java.util.*

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

    fun part2(): Int {
        val connections = readInput(filename)

        val results = mutableMapOf<Pair<String, String>, List<Path>>()
        results[Pair("out", "out")] = listOf(Path("out", null))

        dfs("svr", "out", connections, emptySet(), results)

        val paths = results[Pair("svr", "out")]!!
        println(paths.size)
        paths.forEach { println(it) }

        val goodPaths = paths.filter { it.contains("dac") && it.contains("fft") }

        return goodPaths.size
    }
}

fun dfs(
    from: String,
    to: String,
    connections: Map<String, List<String>>,
    visited: Set<String>,
    results: MutableMap<Pair<String, String>, List<Path>>
) {
    if (from == to) {
        return
    } else if (visited.contains(from)) {
        return
    } else if (results.contains(Pair(from, to))) {
        return
    } else {
        val nextVisited = visited + from
        val nextPaths = mutableListOf<Path>()
        for (nextItem in connections[from]!!) {
            dfs(nextItem, to, connections, nextVisited, results)
            nextPaths.addAll(results[Pair(nextItem, to)]!!)
        }
        if (nextPaths.isNotEmpty()) {
            val paths = nextPaths.map { Path(from, null).extend(it) }
            results[Pair(from, to)] = paths
        } else {
            results[Pair(from, to)] = listOf()
        }
    }
}

data class PartialResult(val dac: Boolean, val fft: Boolean)

data class Path(val last: String, val parent: Path?) {
    fun extend(item: String): Path = Path(item, this)
    fun extend(path: Path): Path {
        var newPath = Path(last, null)
        path.items().forEach { newPath = newPath.extend(it) }
        return newPath
    }
    fun contains(item: String): Boolean = last == item || (parent != null && parent.contains(item))
    fun items(): List<String> = if (parent != null) parent.items() + last else listOf(last)
    override fun toString(): String {
        return items().toString()
    }
}

fun readInput(filename: String): Map<String, List<String>> {
    return resourceFile(filename).readLines().associate {
        val (from, tostr) = it.split(": ")
        val tos = tostr.split(" ").toList()
        Pair(from, tos)
    }
}
