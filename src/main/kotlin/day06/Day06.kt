package day06

import common.resourceFile

fun main() {
    val day = Day06("/day06/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day06(val filename: String) {

    fun part1(): Long = readInput1(filename).sumOf { it.compute() }

    fun part2(): Long = readInput2(filename).sumOf { it.compute() }
}

enum class Operator(val eval: (Long, Long) -> Long) {
    PLUS(Long::plus),
    MULTIPLY(Long::times)
}

data class Column(val numbers: List<Long>, val operator: Operator) {
    fun compute(): Long = numbers.reduce(operator.eval)
}

fun readInput1(filename: String): List<Column> {
    val lines = resourceFile(filename).readLines()
    val operators = parseOperators(lines.last())
    val numberLists = List(operators.size) { mutableListOf<Long>() }

    lines.dropLast(1).forEach { line ->
        line.trim().split("\\s+".toRegex()).map { it.toLong() }
            .forEachIndexed { index, number -> numberLists[index].add(number) }
    }

    return operators.mapIndexed { index, operator -> Column(numberLists[index], operator) }
}

fun readInput2(filename: String): List<Column> {
    val lines = resourceFile(filename).readLines()
    val operators = parseOperators(lines.last())
    val numberLines = lines.dropLast(1).map { it.toCharArray().iterator() }

    return operators.map { operator ->
        val numbers = mutableListOf<Long>()
        do {
            val slice =
                numberLines.map { if (it.hasNext()) it.next() else "" }.joinToString("").trim()
            if (slice.isNotBlank()) numbers.add(slice.toLong())
        } while (slice.isNotBlank())
        Column(numbers, operator)
    }
}

fun parseOperators(line: String): List<Operator> =
    line.trim().split("\\s+".toRegex()).map { if (it == "+") Operator.PLUS else Operator.MULTIPLY }
