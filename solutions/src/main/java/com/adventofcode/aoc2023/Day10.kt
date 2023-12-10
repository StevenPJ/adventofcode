package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines

class Day10 : Solution() {


    override fun part1(input: String): Int {
        val (network, source) = parse(input)

        return network.dfs(source.name).size / 2
    }

    override fun part2(input: String): Long {
        val (network, source) = parse(input)

        val path = network.dfs(source.name)
        val area = path.shoelaceArea()
        val picksTheorem = area - (path.size / 2) + 1
        return picksTheorem.toLong()
    }

    private fun parse(input: String): Pair<List<Node>, Node> {
        val pipeNetwork = input.nonEmptyLines().pipes()
        val source = pipeNetwork.find { it.first == 'S' }!!
        return Pair(pipeNetwork.map { it.second }, source.second)
    }

    private fun List<String>.pipes() : List<Pair<Char, Node>> {
        return this.flatMapIndexed { y, row ->
            row.mapIndexed { x, pipe ->
                val point = Point(x, y)
                val neighbours = connections.mapIndexedNotNull { idx, (vector, _) ->
                    if (hasValidConnection(point, idx, this)) point.move(vector).name() else null
                }
                Pair(pipe, Node(point.name(), neighbours))
            }
        }
    }

    private val connections = listOf(
            Point(0, -1) to "SLJ|",
            Point(1, 0) to "SLF-",
            Point(0, 1) to "S7F|",
            Point(-1, 0) to "S7J-",
    )

    private fun hasValidConnection(source: Point, connectionIdx: Int, field: List<String>): Boolean {
        val destination = source.move(connections[connectionIdx].first)
        val srcConnectors = connections[connectionIdx].second
        val dstConnectors = connections[connectionIdx xor 2].second
        return Dimensions(field).contains(destination)
                && srcConnectors.contains(field.charAt(source))
                && dstConnectors.contains(field.charAt(destination))
    }
}