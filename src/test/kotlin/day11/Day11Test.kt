package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun part1() {
        assertEquals(5, Day11("/day11/input1.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(2, Day11("/day11/input2.txt").part2())
    }
}
