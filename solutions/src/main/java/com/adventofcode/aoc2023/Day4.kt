package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import kotlin.math.floor
import kotlin.math.pow

class Day4 : Solution() {

    override fun part1(input: String): Int {
        return input.toNumbers()
                .map { it.drop(1) }
                .map { it.size - it.toSet().size }
                .sumOf { floor((2.0).pow(it-1)).toInt() }
    }

    override fun part2(input: String): Int {
        val winsByScorecard = input.toNumbers()
                .map { it.drop(1) }
                .mapIndexed { index, it -> index to (it.size - it.toSet().size) }
        val nScorecards = winsByScorecard.associate { (card, _) -> card to 1 }.toMutableMap()
        for ((card, wins) in winsByScorecard) {
            for (c in card + 1..card + wins) {
                nScorecards[c] = (nScorecards[card] ?: 0) + (nScorecards[c] ?: 0)
            }
        }
        return nScorecards.toList().sumOf { (_, n) -> n }
    }
}