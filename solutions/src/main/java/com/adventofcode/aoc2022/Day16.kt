package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.permutationsChooseK
import com.adventofcode.util.splitIgnoreEmpty


class Day16 : Solution() {

    override fun part1(input: String): Int {
        val graph = parseValves(input)
        val closedValves = graph.nodes.filter { it.value > 0 }.keys
        val paths = graph.paths(listOf("AA") + closedValves)
        return findMaxPressure("AA", 0, 30, paths, graph.nodes, closedValves)
    }

    override fun part2(input: String): Int {
        val graph = parseValves(input)
        val closedValves = graph.nodes.filter { it.value > 0 }.keys
        val paths = graph.paths(listOf("AA") + closedValves)
        return closedValves.permutationsChooseK(closedValves.size / 2).maxOfOrNull { playerValves ->
            findMaxPressure("AA", 0, 26, paths, graph.nodes, playerValves) +
                    findMaxPressure("AA", 0, 26, paths, graph.nodes, closedValves - playerValves)

        } ?: 0
    }

    private fun findMaxPressure(
        currentValve: String,
        elapsed: Int,
        maxTime: Int,
        paths: Map<String, Map<String, Int>>,
        valves: Map<String, Int>,
        closed: Set<String>
    ): Int {
        return closed.maxOfOrNull { valve ->
            val timeToValue = elapsed + paths.getValue(currentValve).getValue(valve) + 1
            if (timeToValue > maxTime) {
                0
            } else {
                val minutesOpen = maxTime - timeToValue
                val totalPressureFromValve = minutesOpen * valves.getValue(valve)
                totalPressureFromValve + findMaxPressure(
                    currentValve = valve,
                    elapsed = timeToValue,
                    maxTime = maxTime,
                    paths = paths,
                    valves = valves,
                    closed = closed - valve
                )
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
}

data class Graph(
    val nodes: MutableMap<String, Int> = mutableMapOf(),
    val edges: MutableMap<String, List<String>> = mutableMapOf()
) {

    fun paths(valves: List<String>): Map<String, Map<String, Int>> {
        return valves.associateWith { getShortestPaths(it) }
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