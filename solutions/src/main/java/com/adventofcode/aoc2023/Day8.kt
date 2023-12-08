package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.lcm
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.repeatIndefinitely
import java.util.function.Predicate

class Day8 : Solution() {


    override fun part1(input: String): Long {
        val (instructions, network) = parse(input)

        return pathLength(instructions, network, network.find{ it.name == "AAA" }!!) { node -> node == "ZZZ" }
    }

    override fun part2(input: String): Long {
        val (instructions, network) = parse(input)

        return network
                .filter { it.name.endsWith("A") }
                .map { pathLength(instructions, network, it) { node -> node.endsWith("Z") } }
                .lcm()
    }

    private fun parse(input: String): Pair<Sequence<Char>, List<Node>> {
        val instructions = input.nonEmptyLines().first().asSequence().repeatIndefinitely()
        val network = input.nonEmptyLines().drop(1).map {
            val (name, left, right) = it.match("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex())!!
            Node(name, listOf(left, right))
        }
        return Pair(instructions, network)
    }

    private fun pathLength(instructions: Sequence<Char>, network: List<Node>, source: Node, sinkTest: Predicate<String>): Long {
        val instructionItr = instructions.iterator()
        val next: (node: Node) -> Node = { node -> if (instructionItr.next()  == 'L') node.left(network) else node.right(network) }
        return path(next, source) { sinkTest.test(it.name) }.count().toLong() - 1
    }
}
