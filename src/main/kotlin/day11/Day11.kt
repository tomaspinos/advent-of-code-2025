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

    fun part2(): Int {
        val connections = readInput(filename)

        val paths = search("svr", "out", connections)

        return paths.size
    }
}

fun search(from: String, to: String, connections: Map<String, List<String>>): List<Path> {
    var finishedPaths = mutableListOf<Path>()

    val firstPath = Path(from, null)
    var livePaths = mutableListOf<Path>();
    livePaths.addAll(connections[from]!!.map { Path(it, firstPath) })

    while (livePaths.isNotEmpty()) {
        println(livePaths.size)
        val nextLivePaths = mutableListOf<Path>()

        for (path in livePaths) {
            val nextItems = connections[path.last]
            if (nextItems != null) {
                for (nextItem in nextItems) {
                    if (nextItem == to) {
                        finishedPaths.add(path)
                    } else if (!path.contains(nextItem)) {
                        nextLivePaths.add(path.extend(nextItem))
                    }
                }
            }
        }

        livePaths = nextLivePaths
    }

    return finishedPaths
}

data class Path(val last: String, val parent: Path?) {
    fun extend(item: String): Path = Path(item, this)
    fun contains(item: String): Boolean = last == item || (parent != null && parent.contains(item))
}

fun readInput(filename: String): Map<String, List<String>> {
    return resourceFile(filename).readLines().associate {
        val (from, tostr) = it.split(": ")
        val tos = tostr.split(" ").toList()
        Pair(from, tos)
    }
}
