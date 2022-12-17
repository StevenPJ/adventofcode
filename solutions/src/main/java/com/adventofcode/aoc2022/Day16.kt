package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.eachPermutationChooseK
import com.adventofcode.util.splitIgnoreEmpty


class Day16 : Solution() {

    override fun part1(input: String): Int {
        val graph = parseValves(input)
        val closedValves = graph.nodes.filter { it.value > 0 }.keys
        return findMaxPressure("AA", 0, 30, graph.paths(), graph, closedValves)
    }

    override fun part2(input: String): Int {
        val graph = parseValves(input)
        val paths = graph.paths()
        val closedValves = graph.nodes.filter { it.value > 0 }.keys
        val playerValveOptions = getNChooseKValves(closedValves, closedValves.size / 2)
        return playerValveOptions.maxOfOrNull { playerValves ->
            findMaxPressure("AA", 0, 26, paths, graph, playerValves) +
                    findMaxPressure("AA", 0, 26, paths, graph, closedValves - playerValves)

        } ?: 0
    }

    private fun findMaxPressure(
        currentValve: String,
        elapsed: Int,
        maxTime: Int,
        paths: Map<String, Map<String, Int>>,
        graph: Graph,
        closed: Set<String>
    ): Int {
        return closed.maxOfOrNull { valve ->
            val timeToValue = elapsed + paths.getValue(currentValve).getValue(valve) + 1
            if (timeToValue > maxTime) {
                0
            } else {
                val minutesOpen = maxTime - timeToValue
                val totalPressureFromValve = minutesOpen * graph.nodes.getValue(valve)
                totalPressureFromValve + findMaxPressure(
                    currentValve = valve,
                    elapsed = timeToValue,
                    maxTime = maxTime,
                    paths = paths,
                    graph = graph,
                    closed = closed - valve
                )
            }
        } ?: 0
    }

    private fun getNChooseKValves(valves: Set<String>, k: Int): List<Set<String>> {
        val result = hashSetOf<Set<String>>()
        valves.toList().eachPermutationChooseK(k).forEach {
            result.add(it.toHashSet())
        }
        return result.toList()
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