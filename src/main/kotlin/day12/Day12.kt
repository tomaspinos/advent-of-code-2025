package day12

import common.resourceFile

fun main() {
    val day = Day12("/day12/input.txt")
    println(day.part1())
}

class Day12(val filename: String) {

    fun part1(): Int {
        val (shapes, regions) = readInput(filename)
        return regions.count { checkRegion(it, shapes) }
    }
}

fun checkRegion(region: Region, shapes: List<Shape>): Boolean {
    val maxCellsPossible = region.width * region.height
    val minCellsRequired =
        region.shapeCounts.mapIndexed { index, shapeCount -> shapes[index].cellCount * shapeCount }
            .sum()
    return maxCellsPossible >= minCellsRequired
}

data class Shape(val cellCount: Int)

data class Region(val width: Int, val height: Int, val shapeCounts: List<Int>)

fun readInput(filename: String): Pair<List<Shape>, List<Region>> {
    val lines = resourceFile(filename).readLines()

    val shapes = (0..5).map {
        val indexFrom = it * 5
        val shapeLines = lines.subList(indexFrom + 1, indexFrom + 4)
        val cellCount = shapeLines.joinToString("").toCharArray().count { it == '#' }
        Shape(cellCount)
    }

    val regionLines = lines.subList(30, lines.size)
    val regions = regionLines.map {
        val (sizeStr, shapeCountsStr) = it.split(": ")
        val (widthStr, heightStr) = sizeStr.split("x")
        val shapeCounts = shapeCountsStr.split(" ").map(String::toInt)
        Region(widthStr.toInt(), heightStr.toInt(), shapeCounts)
    }

    return Pair(shapes, regions)
}
