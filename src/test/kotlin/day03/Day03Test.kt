package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun part1() {
        assertEquals(357, Day03("/day03/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(3121910778619, Day03("/day03/input.txt").part2())
    }

    @Test
    fun testReadBank() {
        assertEquals(listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1), readBank("987654321111111"))
    }

    @Test
    fun testLargestJoltage1() {
        assertEquals(98, largestJoltage1(readBank("987654321111111")))
        assertEquals(89, largestJoltage1(readBank("811111111111119")))
        assertEquals(78, largestJoltage1(readBank("234234234234278")))
        assertEquals(92, largestJoltage1(readBank("818181911112111")))
    }

    @Test
    fun testLargestJoltage2() {
        assertEquals(987654321111, largestJoltage2(readBank("987654321111111")))
        assertEquals(811111111119, largestJoltage2(readBank("811111111111119")))
        assertEquals(434234234278, largestJoltage2(readBank("234234234234278")))
        assertEquals(888911112111, largestJoltage2(readBank("818181911112111")))
    }
}
