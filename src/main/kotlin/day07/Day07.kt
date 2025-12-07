package day07

import common.XY
import common.resourceFile

fun main() {
    val day = Day07("/day07/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day07(val filename: String) {

    fun part1(): Int {
        val (start, height, splits) = readInput(filename)

        var beams = mutableSetOf<Int>(start.x)
        var beamSplitCount = 0
        for (y in 1..<height - 1) {
            val newBeams = mutableSetOf<Int>()
            for (beam in beams) {
                if (splits.contains(XY(beam, y + 1))) {
                    beamSplitCount++
                    newBeams.add(beam - 1)
                    newBeams.add(beam + 1)
                } else {
                    newBeams.add(beam)
                }
            }
            beams = newBeams
        }

        return beamSplitCount
    }

    fun part2(): Long {
        val (start, height, splits) = readInput(filename)

        var beams = mutableMapOf<Int, Long>()
        beams.put(start.x, 1L)
        for (y in 1..<height - 1) {
            val newBeams = mutableMapOf<Int, Long>()
            for (beamX in beams.keys) {
                if (splits.contains(XY(beamX, y + 1))) {
                    newBeams.merge(beamX - 1, beams[beamX]!!, Long::plus)
                    newBeams.merge(beamX + 1, beams[beamX]!!, Long::plus)
                } else {
                    newBeams.merge(beamX, beams[beamX]!!, Long::plus)
                }
            }
            beams = newBeams
        }

        return beams.values.sum()
    }
}

data class Grid(val start: XY, val height: Int, val splits: Set<XY>)

fun readInput(filename: String): Grid {
    var start: XY? = null
    val splits = mutableSetOf<XY>()
    val lines = resourceFile(filename).readLines()
    for (y in 0..<lines.size) {
        for (x in 0..<lines[y].length) {
            when (lines[y][x]) {
                'S' -> start = XY(x, y)
                '^' -> splits.add(XY(x, y))
            }
        }
    }
    return Grid(start!!, lines.size, splits)
}