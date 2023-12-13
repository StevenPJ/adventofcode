package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines

class Day13 : Solution() {


    override fun part1(input: String): Int {
        val mirrors = parse(input)
        return mirrors.sumOf { (100 * it.reflectionIdx()) + it.transposed().reflectionIdx() }
    }

    override fun part2(input: String): Int {
        val mirrors = parse(input)
        return mirrors.sumOf { (100 * it.reflectionIdx(1)) + it.transposed().reflectionIdx(1) }
    }

    private fun parse(input: String): List<Mirror> {
        return input.splitBlocks().map { Mirror(it.nonEmptyLines().map { row -> row.toCharArray().toList() }) }
    }

    private data class Mirror(val mirror: List<List<Char>>) {
        val transposed: () -> Mirror = { Mirror(mirror.transpose()) }

        fun reflectionIdx(smudgesAllowed: Long = 0): Int {
            for (idx in 1 until mirror.size) {
                val left = mirror.subList(0, idx).reversed()
                val right = mirror.subList(idx, mirror.size)
                val totalMistakes = left.zip(right).sumOf{(r1, r2) -> r1.zip(r2).sumOf{(a, b) -> if (a == b) 0 else 1L } }
                if (totalMistakes == smudgesAllowed)
                    return idx
            }
            return 0
        }
    }
}