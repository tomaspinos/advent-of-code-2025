package day09

import common.XY
import common.resourceFile
import day09.HorizontalCrossing.*
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val day = Day09("/day09/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day09(val filename: String) {

    fun part1(): Long {
        val points = readInput(filename)
        val rectangles = mutableListOf<Rectangle>()
        for (i in points.indices) {
            for (j in i + 1..<points.size) {
                rectangles.add(Rectangle(points[i], points[j]))
            }
        }
        rectangles.sortByDescending { it.area }

        return rectangles.first().area
    }

    fun part2(): Long {
        val points = readInput(filename)

        val rectangles = mutableListOf<Rectangle>()
        for (i in points.indices) {
            for (j in i + 1..<points.size) {
                rectangles.add(Rectangle(points[i], points[j]))
            }
        }

        rectangles.sortByDescending { it.area }

        val edges = edges(points)

        val hEdgesByY = edges.horizontal.groupBy { it.from.y }
        val twoHEdgesOnTheSameY = hEdgesByY.filter { it.value.size > 1 }.isNotEmpty()
        if (twoHEdgesOnTheSameY) error("Two horizontal edges on the same Y")

        val edgesByY = edgesByY(edges)

        for (i in rectangles.indices) {
            val rectangle = rectangles[i]
            if (checkRectangle(rectangle, edgesByY)) return rectangle.area
        }

        return -1
    }
}

enum class HorizontalCrossing {
    INSIDE, OUTSIDE_LEFT, OUTSIDE_RIGHT, HEADING, TAILING, SPANNING
}

data class Line(val from: Int, val to: Int) {
    fun isInsideHorizontally(other: Line): Boolean = from >= other.from && to <= other.to
}

data class Edge(val from: XY, val to: XY)

data class Edges(val horizontal: List<Edge>, val vertical: List<Edge>)

data class EdgesByY(val horizontal: Map<Int, Line>, val vertical: Map<Int, TreeSet<Int>>)

data class Rectangle(
    val a: XY,
    val b: XY,
    val area: Long = (abs(a.x - b.x).toLong() + 1) * (abs(a.y - b.y).toLong() + 1)
) {
    fun upLeft(): XY {
        return XY(min(a.x, b.x), min(a.y, b.y))
    }

    fun downRight(): XY {
        return XY(max(a.x, b.x), max(a.y, b.y))
    }

    fun horizontalLines(): List<Edge> {
        val upLeft = upLeft()
        val downRight = downRight()
        val lines = mutableListOf<Edge>()
        for (y in upLeft.y..downRight.y) {
            lines.add(Edge(XY(upLeft.x, y), XY(downRight.x, y)))
        }
        return lines
    }
}

fun checkRectangle(rectangle: Rectangle, edges: EdgesByY): Boolean {
    val lines = rectangle.horizontalLines()
    return lines.all { checkLine(it, edges) }
}

fun checkLine(line: Edge, edges: EdgesByY): Boolean {
    val y = line.from.y
    return checkLine(Line(line.from.x, line.to.x), edges.horizontal[y], edges.vertical[y])
}

fun checkLine(line: Line, horizontalEdge: Line?, verticalEdges: TreeSet<Int>?): Boolean {
    if (horizontalEdge == null && verticalEdges == null) {
        // outside the polygon
        return false
    }
    if (horizontalEdge != null && verticalEdges == null) {
        // only horizontal edge
        return line.isInsideHorizontally(horizontalEdge)
    } else if (horizontalEdge == null && verticalEdges != null) {
        // only vertical edges
        return isInsideVerticals(line.from, line.to, verticalEdges)
    } else {
        val hCrossing = checkHorizontalCrossing(line, horizontalEdge!!)
        return when (hCrossing) {
            INSIDE -> true
            OUTSIDE_LEFT -> verticalEdges!!.headSet(line.from, true).size % 2 == 1
            OUTSIDE_RIGHT -> verticalEdges!!.tailSet(line.to, true).size % 2 == 1
            HEADING -> verticalEdges!!.headSet(horizontalEdge.from, true).size % 2 == 1
            TAILING -> verticalEdges!!.tailSet(horizontalEdge.to, true).size % 2 == 1
            SPANNING -> verticalEdges!!.headSet(horizontalEdge.from, true).size % 2 == 1 &&
                    verticalEdges.tailSet(horizontalEdge.to, true).size % 2 == 1
        }
    }
}

fun isInsideVerticals(fromX: Int, toX: Int, verticalEdges: TreeSet<Int>): Boolean {
    val verticalsBefore = verticalEdges.headSet(fromX, true)
    // odd count expected
    if (verticalsBefore.size % 2 == 0) return false
    val verticalsAfter = verticalEdges.tailSet(fromX, false)
    if (verticalsAfter.isEmpty()) {
        error("No vertical after: $fromX - $toX")
    }
    return verticalsAfter.first() >= toX
}

fun checkHorizontalCrossing(line: Line, horizontal: Line): HorizontalCrossing {
    val range = horizontal.from..horizontal.to
    return when {
        line.from in range && line.to in range -> INSIDE
        line.from < horizontal.from && line.to in range -> HEADING
        line.from in range && line.to > horizontal.to -> TAILING
        line.from < horizontal.from && line.to > horizontal.to -> SPANNING
        line.from < horizontal.from && line.to < horizontal.from -> OUTSIDE_LEFT
        line.from > horizontal.to && line.to > horizontal.to -> OUTSIDE_RIGHT
        else -> error("Bad horizontal crossing: $line x $horizontal")
    }
}

fun edges(points: List<XY>): Edges {
    val closedPoints = mutableListOf<XY>()
    closedPoints.addAll(points)
    closedPoints.add(points.first())

    val horizontal = mutableListOf<Edge>()
    val vertical = mutableListOf<Edge>()

    var from = closedPoints[0]
    for (i in 1..closedPoints.lastIndex) {
        val to = closedPoints[i]
        if (from.x == to.x) {
            // vertical edge
            val minY = min(from.y, to.y)
            val maxY = max(from.y, to.y)
            if (minY == maxY) error("Bad edge: $from -> $to")
            if (maxY - minY > 1) {
                // don't include beginning and end - they are part of horizontal edges
                vertical.add(Edge(XY(from.x, minY + 1), XY(from.x, maxY - 1)))
            }
        } else if (from.y == to.y) {
            // horizontal edge
            horizontal.add(Edge(XY(min(from.x, to.x), from.y), XY(max(from.x, to.x), from.y)))
        } else {
            error("Bad edge: $from -> $to")
        }
        from = to
    }

    return Edges(horizontal, vertical)
}

fun edgesByY(edges: Edges): EdgesByY {
    val horizontal = HashMap<Int, Line>()
    val vertical = HashMap<Int, TreeSet<Int>>()

    for (edge in edges.horizontal) {
        if (horizontal.containsKey(edge.from.y)) error("Bad edge: $edge")
        horizontal[edge.from.y] = Line(edge.from.x, edge.to.x)
    }

    for (edge in edges.vertical) {
        for (y in edge.from.y..edge.to.y) {
            if (!vertical.containsKey(y)) vertical[y] = TreeSet()
            vertical[y]!!.add(edge.from.x)
        }
    }

    return EdgesByY(horizontal, vertical)
}

fun readInput(filename: String): List<XY> {
    return resourceFile(filename).readLines().map {
        val (x, y) = it.split(",")
        XY(x.toInt(), y.toInt())
    }
}
