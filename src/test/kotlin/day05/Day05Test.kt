package day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun part1() {
        assertEquals(3, Day05("/day05/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(14, Day05("/day05/input.txt").part2())
    }

    @Test
    fun testReadInput() {
        val (ranges, numbers) = readInput("/day05/input.txt")
        assertEquals(listOf(LongRange(3, 5), LongRange(10, 14), LongRange(16, 20), LongRange(12, 18)), ranges)
        assertEquals(listOf(1L, 5, 8, 11, 17, 32), numbers)
    }

    @Test
    fun testMergeRanges() {
        val (ranges, _) = readInput("/day05/input.txt")
        val mergedRanges = mergeRanges(ranges)
        assertEquals(listOf(LongRange(3, 5), LongRange(10, 20)), mergedRanges)
    }
}
