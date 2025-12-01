package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun part1() {
        assertEquals(3, Day01("/day01/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(6, Day01("/day01/input.txt").part2())
    }
}
