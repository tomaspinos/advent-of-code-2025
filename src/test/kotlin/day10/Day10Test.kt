package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val INPUT = "/day10/input.txt"

class Day10Test {

    @Test
    fun part1() {
        assertEquals(7, Day10(INPUT).part1())
    }

    @Test
    fun part2() {
        assertEquals(1, Day10(INPUT).part2())
    }

    @Test
    fun testReadInput() {
        val machines = readInput(INPUT)
        assertEquals(
            Machine(
                Lights(listOf(false, true, true, false)),
                listOf(listOf(3), listOf(1, 3), listOf(2), listOf(2, 3), listOf(0, 2), listOf(0, 1))
            ), machines[0]
        )
    }
}
