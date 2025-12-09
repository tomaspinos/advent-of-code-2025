package day09

import common.XY
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val INPUT = "/day09/input.txt"

class Day09Test {

    @Test
    fun part2() {
        assertEquals(24, Day09(INPUT).part2())
    }

    @Test
    fun testEdgesAndBorders() {
        val points = readInput(INPUT)
        val (hEdges, vBorders) = edgesAndBorders(points)

        assertTrue { hEdges[0] == null }
        assertEquals(listOf(Edge(XY(7, 1), XY(11, 1))), hEdges[1])
        assertTrue { hEdges[2] == null }
        assertEquals(listOf(Edge(XY(2, 3), XY(7, 3))), hEdges[3])

        assertTrue { vBorders[0] == null }

        assertEquals(2, vBorders[1]!!.size)
        assertEquals(listOf(7, 11), vBorders[1]!!.toList())

        assertEquals(2, vBorders[2]!!.size)
        assertEquals(listOf(7, 11), vBorders[2]!!.toList())

        assertEquals(3, vBorders[3]!!.size)
        assertEquals(listOf(2, 7, 11), vBorders[3]!!.toList())
    }

    @Test
    fun testCheckX() {
        assertFalse { checkX(6, listOf(), TreeSet(listOf(7, 11))) }
        assertTrue { checkX(7, listOf(), TreeSet(listOf(7, 11))) }
        assertTrue { checkX(11, listOf(), TreeSet(listOf(7, 11))) }
        assertFalse { checkX(12, listOf(), TreeSet(listOf(7, 11))) }

        assertFalse { checkX(1, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertTrue { checkX(2, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertTrue { checkX(7, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertTrue { checkX(8, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertTrue { checkX(10, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertTrue { checkX(11, listOf(), TreeSet(listOf(2, 7, 11))) }
        assertFalse { checkX(12, listOf(), TreeSet(listOf(2, 7, 11))) }
    }
}