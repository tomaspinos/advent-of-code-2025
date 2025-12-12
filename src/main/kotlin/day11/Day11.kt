package day11

import common.resourceFile
import day11.ResultStatus.*

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
        val connections = readInput(filename)

        val results = mutableMapOf<Pair<String, String>, PartialResult>()
        results[Pair("out", "out")] = PartialResult(mutableMapOf()).plus(OTHER, 1)

        dfs("svr", "out", connections, emptySet(), results)

        return results[Pair("svr", "out")]!!.get(DAC_AND_FFT)
    }
}

fun dfs(
    from: String,
    to: String,
    connections: Map<String, List<String>>,
    visited: Set<String>,
    results: MutableMap<Pair<String, String>, PartialResult>
) {
    if (from == to) {
        return
    } else if (visited.contains(from)) {
        return
    } else if (results.contains(Pair(from, to))) {
        return
    } else {
        val nextVisited = visited + from
        var myResult = PartialResult(mutableMapOf())
        for (nextItem in connections[from]!!) {
            dfs(nextItem, to, connections, nextVisited, results)
            val nextResult = results[Pair(nextItem, to)]!!
            if (from == "dac") {
                myResult.plus(DAC_AND_FFT, nextResult.get(FFT_ONLY))
                myResult.plus(DAC_ONLY, nextResult.get(OTHER))
            } else if (from == "fft") {
                myResult.plus(DAC_AND_FFT, nextResult.get(DAC_ONLY))
                myResult.plus(FFT_ONLY, nextResult.get(OTHER))
            } else {
                myResult.plus(DAC_AND_FFT, nextResult.get(DAC_AND_FFT))
                myResult.plus(DAC_ONLY, nextResult.get(DAC_ONLY))
                myResult.plus(FFT_ONLY, nextResult.get(FFT_ONLY))
                myResult.plus(OTHER, nextResult.get(OTHER))
            }
        }
        results[Pair(from, to)] = myResult
    }
}

enum class ResultStatus {
    DAC_AND_FFT,
    DAC_ONLY,
    FFT_ONLY,
    OTHER
}

data class PartialResult(val counts: MutableMap<ResultStatus, Long>) {
    fun get(status: ResultStatus): Long = counts.getOrDefault(status, 0)
    fun plus(status: ResultStatus, count: Long): PartialResult {
        counts.merge(status, count, Long::plus)
        return this
    }
}

fun readInput(filename: String): Map<String, List<String>> {
    return resourceFile(filename).readLines().associate {
        val (from, tostr) = it.split(": ")
        val tos = tostr.split(" ").toList()
        Pair(from, tos)
    }
}
