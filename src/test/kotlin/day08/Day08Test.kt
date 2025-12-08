package day08

import common.XYZ
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "/day08/input.txt"

class Day07Test {

    @Test
    fun part1() {
        assertEquals(40, Day08(INPUT).part1(10))
    }

    @Test
    fun part2() {
        assertEquals(25272, Day08(INPUT).part2())
    }

    @Test
    fun testReadInput() {
        val points = readInput(INPUT)
        assertEquals(XYZ(162, 817, 812), points[0])
    }
}
