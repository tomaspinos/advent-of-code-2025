package day09

import common.XY
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "/day09/input.txt"

class Day09Test {

    @Test
    fun part1() {
        assertEquals(50, Day09(INPUT).part1())
    }

    @Test
    fun part2() {
        assertEquals(24, Day09(INPUT).part2())
    }

    @Test
    fun testReadInput() {
        val points = readInput(INPUT)
        assertEquals(XY(7, 1), points[0])
    }
}
