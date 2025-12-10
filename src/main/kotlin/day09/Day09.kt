package day09

import common.XY
import common.resourceFile
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val day = Day09("/day09/input.txt")
    //println(day.part1())
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

        val edgesAndBorders = edgesAndBorders(points)

        for (i in rectangles.indices) {
            val rectangle = rectangles[i]
            if (i % 1 == 0) {
                println("$i/${rectangles.size}: $rectangle")
            }
            if (checkRectangle(rectangle, edgesAndBorders)) {
                return rectangle.area
            }
        }

        return -1
    }
}

data class Edge(val from: XY, val to: XY)

data class EdgesOnY(val byFromX: TreeMap<Int, Edge>, val byToX: TreeMap<Int, Edge>)

data class HEdgesAndVBorders(val hEdges: Map<Int, EdgesOnY>, val vBorders: MutableMap<Int, TreeSet<Int>>)

fun edgesAndBorders(points: List<XY>): HEdgesAndVBorders {
    val hEdges = mutableMapOf<Int, EdgesOnY>()
    val vBorders = mutableMapOf<Int, TreeSet<Int>>()

    val closedPoints = mutableListOf<XY>()
    closedPoints.addAll(points)
    closedPoints.add(points.first())

    var from = closedPoints[0]
    for (i in 1..closedPoints.lastIndex) {
        val to = closedPoints[i]
        if (from.x == to.x) {
            // vertical edge
            for (y in min(from.y, to.y) + 1..<max(from.y, to.y)) {
                if (vBorders.containsKey(y)) {
                    vBorders[y]!!.add(from.x)
                } else {
                    vBorders[y] = TreeSet<Int>().apply { add(from.x) }
                }
            }
        } else {
            // horizontal edge
            if (!hEdges.contains(from.y)) {
                hEdges[from.y] = EdgesOnY(TreeMap(), TreeMap())
            }
            val fromX = min(from.x, to.x)
            val toX = max(from.x, to.x)
            val edge = Edge(XY(fromX, from.y), XY(toX, from.y))
            hEdges[from.y]!!.byFromX.put(fromX, edge)
            hEdges[from.y]!!.byToX.put(toX, edge)
        }
        from = to
    }

    return HEdgesAndVBorders(hEdges, vBorders)
}

fun checkRectangle(rectangle: Rectangle, edgesAndBorders: HEdgesAndVBorders): Boolean {
//    println(rectangle)
    val upLeft = rectangle.upLeft()
    val downRight = rectangle.downRight()
    for (y in upLeft.y..downRight.y) {
        val hEdges = edgesAndBorders.hEdges.getOrElse(y) { EdgesOnY(TreeMap(), TreeMap()) }
        val vBorders = edgesAndBorders.vBorders.getOrElse(y) { TreeSet() }

        var x = upLeft.x
        while (x <= downRight.x) {
            println("$x,$y")

            // inside horizontal edge?
            val edgeOnLeft = hEdges.byFromX.floorEntry(x)
            val edgeOnRight = hEdges.byToX.ceilingEntry(x)
            // the point is inside a horizontal edge
            if (edgeOnLeft != null && edgeOnRight != null && edgeOnLeft.value == edgeOnRight.value) {
                x = edgeOnRight.value!!.to.x + 1
                continue
            }

            // inside vertical edge?
            if (vBorders.contains(x)) {
                x++
                continue
            }

            val headBorders = vBorders.headSet(x)
            if (headBorders.isNotEmpty()) {
                // there is a vertical edge on the left
                if (headBorders.size % 2 == 1) {
                    // inside the polygon
                    val tailBorders = vBorders.tailSet(x)
                    check(tailBorders.isNotEmpty()) { "Lost inside the polygon" }
                    x = tailBorders.first() + 1
                    continue
                } else {
                    // outside the polygon
                    return false
                }
            } else {
                // there is no vertical edge on the left
                // maybe there's a horizontal edge on the left
                if (edgeOnLeft != null) {
                    val yMinu1Inside = edgesAndBorders.vBorders.getOrElse(y - 1) { TreeSet() }.headSet(x).size % 2 == 1
                    val yPlus1Inside = edgesAndBorders.vBorders.getOrElse(y + 1) { TreeSet() }.headSet(x).size % 2 == 1
                    if (yMinu1Inside || yPlus1Inside) {
                        x++
                        continue
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            }
        }
    }

    return true
}

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
}

fun readInput(filename: String): List<XY> {
    return resourceFile(filename).readLines().map {
        val (x, y) = it.split(",")
        XY(x.toInt(), y.toInt())
    }
}
