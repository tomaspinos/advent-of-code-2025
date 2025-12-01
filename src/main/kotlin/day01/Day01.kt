package day01

import common.resourceFile

fun main() {
    val day01 = Day01("/day01/input.txt")
    println(day01.part1())
    println(day01.part2())
}

class Day01(val filename: String) {

    fun part1(): Int {
        var current = 50
        var zeros = 0
        for (it in resourceFile(filename).readLines()) {
            val left = it[0] == 'L'
            val distance = it.substring(1).toInt()
            val multiplier = if (left) -1 else 1
            current = (current + (multiplier * distance)) % 100
            if (current == 0) {
                zeros++
            }
        }
        return zeros
    }

    fun part2(): Int {
        var current = 50
        var zeros = 0
        for (it in resourceFile(filename).readLines()) {
            val left = it[0] == 'L'
            val distance = it.substring(1).toInt()

            val delta = if (left) -1 else 1
            var d = distance
            while (d > 0) {
                current += delta
                if (current % 100 == 0) zeros++
                d--
            }
        }
        return zeros
    }
}
