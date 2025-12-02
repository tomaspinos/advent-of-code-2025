package day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day02Test {

    @Test
    fun part1() {
        assertEquals(1227775554, Day02("/day02/input.txt").part1())
    }

    @Test
    fun part2() {
        assertEquals(4174379265, Day02("/day02/input.txt").part2())
    }

    @Test
    fun isInvalid2_number() {
        assertTrue(isInvalid2(11))
        assertTrue(isInvalid2(111))
        assertTrue(isInvalid2(1010))
        assertTrue(isInvalid2(222222))
        assertTrue(isInvalid2(38593859))
        assertTrue(isInvalid2(1188511885))
    }

    @Test
    fun isInvalid2_string_rep() {
        assertTrue(isInvalid2("11", 1))
        assertTrue(isInvalid2("111", 1))
        assertTrue(isInvalid2("1010", 2))
        assertTrue(isInvalid2("222222", 3))
        assertTrue(isInvalid2("38593859", 4))
        assertTrue(isInvalid2("1188511885", 5))
    }
}
