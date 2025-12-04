package day04

import common.XY
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun part1() {
        assertEquals(13, Day04("/day04/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(43, Day04("/day04/input.txt").part2())
    }

    @Test
    fun testReadGrid() {
        val grid = readGrid("/day04/input.txt")
        assertArrayEquals(arrayOf(false, false, true, true, false, true, true, true, true, false), grid[0])
    }

    @Test
    fun testIsAccessible() {
        val grid = readGrid("/day04/input.txt")
        assertEquals(true, isAccessible(grid, XY(2, 0)))
        assertEquals(true, isAccessible(grid, XY(3, 0)))
    }
}
