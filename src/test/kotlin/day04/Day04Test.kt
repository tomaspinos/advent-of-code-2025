package day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun part1() {
        assertEquals(13, Day04("/day04/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(1, Day04("/day04/input.txt").part2())
    }
}
