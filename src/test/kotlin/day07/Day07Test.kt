package day07

import common.XY
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val INPUT = "/day07/input.txt"

class Day07Test {

    @Test
    fun part1() {
        assertEquals(21, Day07(INPUT).part1())
    }

    @Test
    fun part2() {
        assertEquals(40, Day07(INPUT).part2())
    }

    @Test
    fun testReadInput() {
        val (start, size, splits) = readInput(INPUT)
        assertEquals(XY(7, 0), start)
        assertEquals(16, size)
        assertFalse { splits.contains(XY(7, 1)) }
        assertTrue { splits.contains(XY(7, 2)) }
    }
}