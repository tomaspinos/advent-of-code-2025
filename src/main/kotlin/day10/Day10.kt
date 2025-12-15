package day10

import common.resourceFile
import kotlin.math.min

fun main() {
    val day = Day10("/day10/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day10(val filename: String) {

    fun part1(): Int {
        val machines = readInput(filename)
        return machines.sumOf { machine ->
            println(machine)
            val initialLights = mutableListOf<Boolean>()
            repeat(machine.lights.state.size) { initialLights.add(false) }
            val lightHistory = mutableMapOf<Lights, Int>()
            lightHistory[Lights(initialLights)] = 0
            dfs(machine, Lights(initialLights), 0, listOf(), lightHistory)
            println(lightHistory[machine.lights]!!)
            lightHistory[machine.lights]!!
        }
    }

    fun part2(): Long {
        readInput(filename)
        return 1
    }
}

fun dfs(
    machine: Machine,
    lights: Lights,
    presses: Int,
    togglesPressed: List<List<Int>>,
    lightHistory: MutableMap<Lights, Int>
) {
    if (presses > 10) return
    for (toggle in machine.toggles) {
        val newLights = lights.toggle(toggle)

        if (lightHistory.contains(newLights) && lightHistory[newLights]!! < presses + 1) continue

        val newTogglesPressed = togglesPressed + listOf(toggle)

//        println("${newLights}:${lightHistory[newLights]} $newTogglesPressed")

        lightHistory[newLights] = presses + 1

        if (newLights == machine.lights) {
//            println(newLights)
//            println("${togglesPressed.size} -> $togglesPressed")
            continue
        }

        dfs(machine, newLights, presses + 1, newTogglesPressed, lightHistory)
    }
}

data class Machine(val lights: Lights, val toggles: List<List<Int>>)

data class Lights(val state: List<Boolean>, val string: String = state.joinToString("") { if (it) "#" else "."}) {
    fun toggle(toggle: List<Int>): Lights {
        val newState = state.toMutableList()
        toggle.forEach { newState[it] = !state[it] }
        return Lights(newState)
    }

    override fun equals(other: Any?): Boolean {
        return other is Lights && string == other.string
    }

    override fun hashCode(): Int {
        return string.hashCode()
    }

    override fun toString(): String = string
}

fun readInput(filename: String): List<Machine> {
    return resourceFile(filename).readLines().map {
        val lightsRBracket = it.indexOf(']')
        val joltageLBracket = it.indexOf('{')
        val lights = it.substring(1, lightsRBracket).toCharArray()
            .map { it == '#' }
            .toList()
        val toggles = it.substring(lightsRBracket + 2, joltageLBracket - 1)
            .split(" ")
            .map {
                it.substring(1, it.length - 1)
                    .split(",")
                    .map { it.toInt() }
                    .toList()
            }
        Machine(Lights(lights), toggles)
    }
}
