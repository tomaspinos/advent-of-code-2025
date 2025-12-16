package day10

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import common.resourceFile

fun main() {
    val day = Day10("/day10/input.txt")
    println(day.part1())
    println(day.part2())
}

class Day10(val filename: String) {

    fun part1(): Int {
        val machines = readInput(filename)
        return machines.mapIndexed { index, machine ->
            println(machine)
            val initialLights = mutableListOf<Boolean>()
            repeat(machine.lights.state.size) { initialLights.add(false) }
            val lightHistory = mutableMapOf<Lights, Int>()
            lightHistory[Lights(initialLights)] = -1
            dfs(machine, Lights(initialLights), 0, lightHistory)
            println("${index + 1}/${machines.size}: ${lightHistory[machine.lights]!!}")
            lightHistory[machine.lights]!!
        }.sum()
    }

    fun part2(): Long {
        Loader.loadNativeLibraries()
        val machines = readInput(filename)
        return machines.sumOf { machine -> lp(machine) }
    }
}

fun dfs(
    machine: Machine,
    lights: Lights,
    presses: Int,
    lightHistory: MutableMap<Lights, Int>
) {
    if ((lightHistory[machine.lights] ?: Int.MAX_VALUE) < presses + 1) return

    for (toggle in machine.toggles) {
        val newLights = lights.toggle(toggle)

        if (lightHistory.contains(newLights) && lightHistory[newLights]!! < presses + 1) continue

        lightHistory[newLights] = presses + 1

        if (newLights == machine.lights) continue

        dfs(machine, newLights, presses + 1, lightHistory)
    }
}

fun lp(machine: Machine): Long {
    val solver = MPSolver.createSolver("SCIP")!!

    val togglePresses = machine.toggles.indices
        .map { solver.makeIntVar(0.0, Double.MAX_VALUE, "toggle_$it") }

    machine.joltages.forEachIndexed { j, joltage ->
        val constraint = solver.makeConstraint(joltage.toDouble(), joltage.toDouble(), "joltage_$j")
        machine.toggles.forEachIndexed { t, toggle ->
            if (j in toggle) {
                constraint.setCoefficient(togglePresses[t], 1.0)
            }
        }
    }

    val objective = solver.objective()
    for (togglePress in togglePresses) objective.setCoefficient(togglePress, 1.0)
    objective.setMinimization()

    solver.solve()

    return objective.value().toLong()
}

data class Machine(val lights: Lights, val toggles: List<List<Int>>, val joltages: List<Int>)

data class Lights(
    val state: List<Boolean>,
    val string: String = state.joinToString("") { if (it) "#" else "." }
) {
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
        val joltages =
            it.substring(joltageLBracket + 1, it.length - 1).split(",").map { it.toInt() }
        Machine(Lights(lights), toggles, joltages)
    }
}
