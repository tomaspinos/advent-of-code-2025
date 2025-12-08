package day08

import common.XYZ
import common.resourceFile

fun main() {
    val day = Day08("/day08/input.txt")
    println(day.part1(1000))
    println(day.part2())
}

class Day08(val filename: String) {

    fun part1(n: Int): Int {
        val points = readInput(filename)
        return go1(points, n)
    }

    fun part2(): Long {
        val points = readInput(filename)
        return go2(points)
    }
}

fun go1(points: List<XYZ>, n: Int): Int {
    val connections = mutableListOf<Connection>()
    for (i in points.indices) {
        for (j in i + 1..<points.size) {
            connections.add(Connection(points[i], points[j]))
        }
    }

    connections.sortBy { it.length }

    val circuits = mutableListOf<MutableSet<XYZ>>()
    val pointsToCircuits = mutableMapOf<XYZ, MutableSet<XYZ>>()

    for (i in 0..<n) {
        val connection = connections[i]
        val (from, to) = connection
        val fromCircuit = pointsToCircuits[from]
        val toCircuit = pointsToCircuits[to]
        when {
            fromCircuit == null && toCircuit == null -> {
                val newCircuit = mutableSetOf(from, to)
                circuits.add(newCircuit)
                pointsToCircuits[from] = newCircuit
                pointsToCircuits[to] = newCircuit
            }

            fromCircuit != null && toCircuit == null -> {
                fromCircuit.add(to)
                pointsToCircuits[to] = fromCircuit
            }

            fromCircuit == null && toCircuit != null -> {
                toCircuit.add(from)
                pointsToCircuits[from] = toCircuit
            }

            fromCircuit != null && toCircuit != null && fromCircuit != toCircuit -> {
                val newCircuit = mutableSetOf<XYZ>()
                newCircuit.addAll(fromCircuit)
                newCircuit.addAll(toCircuit)
                circuits.remove(fromCircuit)
                circuits.remove(toCircuit)
                circuits.add(newCircuit)
                newCircuit.forEach { pointsToCircuits[it] = newCircuit }
            }
        }
    }

    val threeLargestSizes = circuits.map { it.size }.sortedDescending().take(3)
    return threeLargestSizes.reduce { a, b -> a * b }
}

fun go2(points: List<XYZ>): Long {
    val connections = mutableListOf<Connection>()
    for (i in points.indices) {
        for (j in i + 1..<points.size) {
            connections.add(Connection(points[i], points[j]))
        }
    }

    connections.sortBy { it.length }

    val circuits = mutableListOf<MutableSet<XYZ>>()
    val pointsToCircuits = mutableMapOf<XYZ, MutableSet<XYZ>>()

    var i = 0
    while (pointsToCircuits.size < points.size) {
        val connection = connections[i]
        i++
        val (from, to) = connection
        val fromCircuit = pointsToCircuits[from]
        val toCircuit = pointsToCircuits[to]
        when {
            fromCircuit == null && toCircuit == null -> {
                val newCircuit = mutableSetOf(from, to)
                circuits.add(newCircuit)
                pointsToCircuits[from] = newCircuit
                pointsToCircuits[to] = newCircuit
            }

            fromCircuit != null && toCircuit == null -> {
                fromCircuit.add(to)
                pointsToCircuits[to] = fromCircuit
            }

            fromCircuit == null && toCircuit != null -> {
                toCircuit.add(from)
                pointsToCircuits[from] = toCircuit
            }

            fromCircuit != null && toCircuit != null && fromCircuit != toCircuit -> {
                val newCircuit = mutableSetOf<XYZ>()
                newCircuit.addAll(fromCircuit)
                newCircuit.addAll(toCircuit)
                circuits.remove(fromCircuit)
                circuits.remove(toCircuit)
                circuits.add(newCircuit)
                newCircuit.forEach { pointsToCircuits[it] = newCircuit }
            }
        }
    }

    val lastConnection = connections[i - 1]
    return lastConnection.from.x.toLong() * lastConnection.to.x.toLong()
}

data class Connection(val from: XYZ, val to: XYZ, val length: Double = from.distanceTo(to))

fun readInput(filename: String): List<XYZ> {
    return resourceFile(filename).readLines().map {
        val (x, y, z) = it.split(",")
        XYZ(x.toInt(), y.toInt(), z.toInt())
    }
}
