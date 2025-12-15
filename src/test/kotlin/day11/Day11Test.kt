package day11

import day10.Day10
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {

    @Test
    fun part1() {
        assertEquals(5, Day10("/day11/input1.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(2, Day11("/day11/input2.txt").part2())
    }
}
