package day03

import common.resourceFile
import kotlin.math.pow

fun main() {
    val day03 = Day03("/day03/input.txt")
    println(day03.part1())
    println(day03.part2())
}

class Day03(val filename: String) {

    fun part1(): Int {
        val banks = readBanks(filename)
        return banks.sumOf { largestJoltage1(it) }
    }

    fun part2(): Long {
        val banks = readBanks(filename)
        return banks.sumOf { largestJoltage2(it) }
    }
}

fun largestJoltage1(bank: List<Int>): Int {
    var firstMax = bank[0]
    var secondMax = 0
    for (i in 1..<bank.lastIndex) {
        val d = bank[i]
        if (d > firstMax) {
            firstMax = d
            secondMax = bank[i + 1]
        } else if (d > secondMax) {
            secondMax = d
        }
    }
    if (bank[bank.lastIndex] > secondMax) secondMax = bank[bank.lastIndex]
    return firstMax * 10 + secondMax
}

fun largestJoltage2(bank: List<Int>): Long {
    return largestJoltage2(bank, 12)
}

fun largestJoltage2(bank: List<Int>, remaining: Int): Long {
    val possibleDigits = bank.subList(0, bank.size - (remaining - 1))
    val d = possibleDigits.max()
    if (remaining > 1) {
        val indexOfD = possibleDigits.indexOf(d)
        val remainingBank = bank.subList(indexOfD + 1, bank.size)
        return d * 10.0.pow(remaining - 1).toLong() + largestJoltage2(remainingBank, remaining - 1)
    } else {
        return d.toLong()
    }
}

fun readBanks(filename: String): List<List<Int>> {
    return resourceFile(filename).readLines().map { readBank(it) }
}

fun readBank(s: String): List<Int> {
    return s.toCharArray().map { c -> Integer.parseInt(c.toString()) }.toList()
}
