package day02

import common.resourceFile

fun main() {
    val day02 = Day02("/day02/input.txt")
    println(day02.part1())
    println(day02.part2())
}

class Day02(val filename: String) {

    fun part1(): Long {
        return readRanges().sumOf { it.sumInvalid1() }
    }

    fun part2(): Long {
        return readRanges().sumOf { it.sumInvalid2() }
    }

    fun readRanges(): List<Range> {
        return resourceFile(filename).readLines().first().split(",")
            .map {
                val range = it.split("-")
                Range(range[0].toLong(), range[1].toLong())
            }
    }
}

data class Range(val start: Long, val end: Long) {
    fun sumInvalid1(): Long {
        return (start..end).filter { isInvalid1(it) }.sum()
    }

    fun sumInvalid2(): Long {
        return (start..end).filter { isInvalid2(it) }.sum()
    }
}

fun isInvalid1(n: Long): Boolean {
    val s = n.toString()
    if (s.length % 2 == 1) return false
    val a = s.substring(0, s.length / 2)
    val b = s.substring(s.length / 2, s.length)
    return a == b
}

fun isInvalid2(n: Long): Boolean {
    val s = n.toString()
    for (r in 1..(s.length / 2)) {
        if (isInvalid2(s, r)) return true
    }
    return false
}

fun isInvalid2(s: String, r: Int): Boolean {
    if (s.length % r > 0) return false
    val frag = s.substring(0, r)
    for (i in 1..<(s.length / r)) {
        var nextFrag = s.substring(i * r, i * r + r)
        if (nextFrag != frag) return false
    }
    return true
}
