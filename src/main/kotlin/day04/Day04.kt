package day04

import common.resourceFile

fun main() {
    val day = Day04("/day04/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day04(val filename: String) {

    fun part1(): Int {
        val grid = readGrid(filename)
        return 1
    }

    fun part2(): Int {
        return 1
    }
}

fun readGrid(filename: String): Array<Array<Boolean>> =
    resourceFile(filename).readLines().map { it.map { c -> c == '@' }.toTypedArray() }.toTypedArray()
