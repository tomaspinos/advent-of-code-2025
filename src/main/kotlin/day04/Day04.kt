package day04

import common.*

fun main() {
    val day = Day04("/day04/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day04(val filename: String) {

    fun part1(): Int {
        val grid = readGrid(filename)
//        print(grid)
//        println()
//        printResult(grid)
//        println()
        var accessibleCells = 0
        forEach(grid) { xy ->
            if (value(grid, xy) && isAccessible(grid, xy)) accessibleCells++
        }
        return accessibleCells
    }

    fun part2(): Int {
        var grid = readGrid(filename)
        var removed = 0
        var round = 0
        var shouldDoNextRound = true
        while (shouldDoNextRound) {
            round++
            shouldDoNextRound = false
            val gridCopy = Array(grid.size) { grid[it].copyOf() }
            forEach(grid) { xy ->
                if (value(grid, xy) && isAccessible(grid, xy)) {
                    setValue(gridCopy, xy, false)
                    removed++
                    shouldDoNextRound = true
                }
            }
//            println("Round $round")
//            println()
//            print(grid)
//            println()
//            print(gridCopy)
//            println()
            grid = gridCopy
        }
        return removed
    }
}

fun countNeighboringRolls(grid: Array<Array<Boolean>>, xy: XY): Int =
    xy.neighbors8(grid).count { it }

fun isAccessible(grid: Array<Array<Boolean>>, xy: XY): Boolean = countNeighboringRolls(grid, xy) < 4

fun readGrid(filename: String): Array<Array<Boolean>> =
    resourceFile(filename).readLines().map { it.map { c -> c == '@' }.toTypedArray() }
        .toTypedArray()

fun print(grid: Array<Array<Boolean>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            print(if (grid[y][x]) "@" else ".")
        }
        println()
    }
}

fun printNeighboringRolls(grid: Array<Array<Boolean>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            print(if (grid[y][x]) countNeighboringRolls(grid, XY(x, y)) else ".")
        }
        println()
    }
}

fun printResult(grid: Array<Array<Boolean>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x]) {
                if (isAccessible(grid, XY(x, y))) print("x") else print("@")
            } else {
                print(".")
            }
        }
        println()
    }
}