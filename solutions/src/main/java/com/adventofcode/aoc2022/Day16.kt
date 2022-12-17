package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day16 : Solution() {

    override fun part1(input: String): Int {
        val graph = parseValves(input)
        return findMaxPressure(State.start(graph), 30)
    }

    override fun part2(input: String): Int {
        val graph = parseValves(input)
        return findMaxPressureWithHelp(State.start(graph), State.start(graph), 26)
    }

    private fun findMaxPressure(state: State, maxTime: Int): Int {
        return state.workingValves.maxOfOrNull { valve ->
            val timeToValue = state.timeToMoveToValve(valve)
            if (timeToValue > maxTime || state.hasOpened(valve)) {
                0
            } else {
                val minutesOpen = maxTime - timeToValue
                val totalPressureFromValve = minutesOpen * state.flowRate(valve)
                totalPressureFromValve + findMaxPressure(
                    state = state.next(valve, timeToValue),
                    maxTime = maxTime
                )
            }
        } ?: 0
    }

    private fun findMaxPressureWithHelp(player: State, elephant: State, maxTime: Int): Int {
        return player.workingValves.maxOfOrNull{ valve ->
            val timeToValue = player.timeToMoveToValve(valve)
            if (timeToValue > maxTime || player.hasOpened(valve)) {
                0
            } else {
                val minutesOpen = maxTime - timeToValue
                val totalPressureFromValve = minutesOpen * player.flowRate(valve)
                totalPressureFromValve + (elephant.workingValves.maxOfOrNull { elephantValve ->
                    val elephantTimeToValve = elephant.timeToMoveToValve(elephantValve)
                    if (elephantTimeToValve > maxTime || elephant.hasOpened(elephantValve) || elephantValve == valve) {
                        0
                    } else {
                        val elephantMinutesOpen = maxTime - elephantTimeToValve
                        val totalPressureFromElephant = elephantMinutesOpen * elephant.flowRate(elephantValve)
                        val opened = player.openedValves + valve + elephantValve
                        totalPressureFromElephant + findMaxPressureWithHelp(
                            player = player.next(valve, timeToValue, opened),
                            elephant = elephant.next(elephantValve, elephantTimeToValve, opened),
                            maxTime = maxTime
                        )
                    }
                } ?: 0)
            }
        } ?: 0
    }

    private fun parseValves(input: String): Graph {
        val graph = Graph()
        input.splitIgnoreEmpty("\n").forEach { line ->
            val regex = """Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
            val matchResult = regex.find(line)
            val (valve, flowRate, neighbours) = matchResult!!.destructured
            graph.nodes[valve] = flowRate.toInt()
            graph.edges[valve] = neighbours.split(',').map { it.trim() }
        }
        return graph
    }

    private fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
        return this.split(*delimiters).filter {
            it.isNotEmpty()
        }
    }
}

class State(
    private val currentValve: String,
    private val timeElapsed: Int,
    private val paths: Map<String, Map<String, Int>>,
    val workingValves: Set<String>,
    private val graph: Graph,
    val openedValves: Set<String>
) {
    companion object {
        fun start(graph: Graph): State {
            val workingValves = graph.nodes.filter { it.value > 0 }.keys
            return State("AA", 0, graph.paths(), workingValves, graph, hashSetOf())
        }
    }

    fun hasOpened(valve: String): Boolean {
        return openedValves.contains(valve)
    }

    fun timeToMoveToValve(valve: String): Int {
        return timeElapsed + paths.getValue(currentValve).getValue(valve) + 1
    }

    fun flowRate(valve: String): Int {
        return graph.nodes.getValue(valve)
    }

    fun next(next: String, timeElapsed: Int): State {
        return State(next, timeElapsed, paths, workingValves, graph, openedValves + next)
    }

    fun next(next: String, timeElapsed: Int, openedValves: Set<String>): State {
        return State(next, timeElapsed, paths, workingValves, graph, openedValves)
    }
}

class Graph(
    val nodes: MutableMap<String, Int> = mutableMapOf(),
    val edges: MutableMap<String, List<String>> = mutableMapOf()
) {

    fun paths(): Map<String, Map<String, Int>> {
        return nodes.keys.associateWith { getShortestPaths(it) }
    }

    private fun getShortestPaths(source: String): Map<String, Int> {
        var unsettled = ArrayDeque(listOf(source))
        val settled = mutableListOf<String>()
        val paths = hashMapOf(source to 0).withDefault { Int.MAX_VALUE }
        while (!unsettled.isEmpty()) {
            val evaluation = unsettled.removeFirst()
            edges[evaluation]?.forEach {
                if (!settled.contains(it)) {
                    val sourceDistance: Int = paths[evaluation] ?: Int.MAX_VALUE
                    if (sourceDistance + 1 < (paths[it] ?: Int.MAX_VALUE)) {
                        paths[it] = sourceDistance + 1
                    }
                    unsettled.add(it)
                }
            }
            settled.add(evaluation)
            unsettled = ArrayDeque(unsettled.distinct().sortedBy { paths[it] })
        }
        return paths
    }
}