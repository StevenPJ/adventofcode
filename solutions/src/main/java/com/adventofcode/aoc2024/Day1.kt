package com.adventofcode.aoc2024

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import kotlin.math.abs

class Day1 : Solution() {

    private fun lists(input: String): Pair<List<Int>, List<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.nonEmptyLines().forEach { line ->
            val (l, r) = line.splitIgnoreEmpty(" ")
            left.add(l.toInt())
            right.add(r.toInt())
        }
        return Pair(left, right)
    }

    override fun part1(input: String): Int {
        val (left, right) = lists(input)
        return left.sorted().zip(right.sorted()) { x, y -> abs(x-y)}.sum()
    }

    override fun part2(input: String): Int {
        val (left, right) = lists(input)
        return left.sumOf { l -> right.count { it == l } * l }
    }
}