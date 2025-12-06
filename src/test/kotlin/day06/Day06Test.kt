package day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "/day06/input.txt"

class Day06Test {

    @Test
    fun part1() {
        assertEquals(4277556, Day06(INPUT).part1())
    }

    @Test
    fun part2() {
        assertEquals(3263827, Day06(INPUT).part2())
    }

    @Test
    fun testReadInput1() {
        val columns = readInput1(INPUT)
        assertEquals(Column(listOf(123L, 45L, 6L), Operator.MULTIPLY), columns[0])
        assertEquals(Column(listOf(328L, 64L, 98L), Operator.PLUS), columns[1])
    }

    @Test
    fun testReadInput2() {
        val columns = readInput2(INPUT)
        assertEquals(Column(listOf(1L, 24L, 356L), Operator.MULTIPLY), columns[0])
        assertEquals(Column(listOf(369L, 248L, 8L), Operator.PLUS), columns[1])
    }

    @Test
    fun testComputeColumn() {
        assertEquals(33210, computeColumn(Column(listOf(123L, 45L, 6L), Operator.MULTIPLY)))
        assertEquals(490, computeColumn(Column(listOf(328L, 64L, 98L), Operator.PLUS)))
    }
}
