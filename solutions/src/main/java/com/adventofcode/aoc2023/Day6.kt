package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Day6 : Solution() {

    override fun part1(input: String): Int {
        val (times, distances) = input.toNumbers()
        return times
                .mapIndexed{ idx, time ->
                    // solve the inequality
                    val roots = solveQuadratic(-1, time, -distances[idx])
                    // return number of integers between the roots
                    (ceil(max(roots.first, roots.second) - 1) - floor(min(roots.first, roots.second))).toInt()
                }
                .reduce(Int::times)
    }

    override fun part2(input: String): Int {
        return part1(input.replace(" ", ""))
    }
}