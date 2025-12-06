package day06

import common.resourceFile

fun main() {
    val day = Day06("/day06/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day06(val filename: String) {

    fun part1(): Long {
        val columns = readInput1(filename)
        return columns.sumOf { computeColumn(it) }
    }

    fun part2(): Long {
        val columns = readInput2(filename)
        return columns.sumOf { computeColumn(it) }
    }
}

fun computeColumn(column: Column): Long {
    return column.numbers.reduce { acc, num -> column.operator.eval(acc, num) }
}

enum class Operator {
    PLUS, MULTIPLY;

    fun eval(a: Long, b: Long): Long {
        return when (this) {
            PLUS -> a + b
            MULTIPLY -> a * b
        }
    }
}

data class Column(val numbers: List<Long>, val operator: Operator)

fun readInput1(filename: String): List<Column> {
    val lines = resourceFile(filename).readLines()
    val operators = lines.last().trim().split("\\s+".toRegex())
        .map { if (it == "+") Operator.PLUS else Operator.MULTIPLY }
    val numberLists = ArrayList<ArrayList<Long>>()
    operators.forEach { numberLists.add(ArrayList()) }

    lines.dropLast(1).forEach { line ->
        val numbers = line.trim().split("\\s+".toRegex()).map { it.toLong() }
        operators.forEachIndexed { index, operator ->
            numberLists[index].add(numbers[index])
        }
    }

    return operators.mapIndexed { index, operator -> Column(numberLists[index], operator) }
}

fun readInput2(filename: String): List<Column> {
    val lines = resourceFile(filename).readLines()
    val operators = lines.last().trim().split("\\s+".toRegex())
        .map { if (it == "+") Operator.PLUS else Operator.MULTIPLY }

    val numberLines = lines.dropLast(1).map { it.toCharArray().iterator() }
    val columns = ArrayList<Column>()
    for (operator in operators) {
        val numbers = ArrayList<Long>()
        do {
            val slice =
                numberLines.map { if (it.hasNext()) it.next() else "" }.joinToString("").trim()
            if (slice.isNotBlank()) {
                numbers.add(slice.toLong())
            }
        } while (slice.isNotBlank())
        columns.add(Column(numbers, operator))
    }

    return columns
}
