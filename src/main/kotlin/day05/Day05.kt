package day05

import common.resourceFile

fun main() {
    val day = Day05("/day05/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day05(val filename: String) {

    fun part1(): Int {
        val (ranges, numbers) = readInput(filename)
        return numbers.count { n -> ranges.any { r -> n in r } }
    }

    fun part2(): Long {
        val (ranges, _) = readInput(filename)
        return mergeRanges(ranges).sumOf { it.last - it.first + 1 }
    }
}

fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
    val sortedRanges = ranges.sortedBy { it.first }
    val mergedRanges = ArrayList<LongRange>()
    var i = 0
    while (i < sortedRanges.size) {
        var range = sortedRanges[i]
        i++
        while (i < sortedRanges.size && range.overlaps(sortedRanges[i])) {
            range = range.merge(sortedRanges[i])
            i++
        }
        mergedRanges.add(range)
    }
    return mergedRanges
}

fun LongRange.overlaps(other: LongRange): Boolean = this.first <= other.last && this.last >= other.first

fun LongRange.merge(other: LongRange): LongRange = minOf(this.first, other.first)..maxOf(this.last, other.last)

data class Input(val ranges: List<LongRange>, val numbers: List<Long>)

fun readInput(filename: String): Input {
    val lines = resourceFile(filename).readLines()
    val ranges = lines.subList(0, lines.indexOf("")).map {
        val (start, end) = it.split("-")
        start.toLong()..end.toLong()
    }
    val numbers = lines.subList(lines.indexOf("") + 1, lines.size).map { it.toLong() }
    return Input(ranges, numbers)
}
