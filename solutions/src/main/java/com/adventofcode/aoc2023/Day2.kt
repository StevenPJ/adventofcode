package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty

class Day2 : Solution() {

    override fun part1(input: String): Int {
        return input.nonEmptyLines()
                .filter {
                    it
                            .splitIgnoreEmpty(";")
                            .none { subset ->
                                parseBallsFromSubset(subset, "red") > 12
                                        || parseBallsFromSubset(subset, "blue") > 14
                                        || parseBallsFromSubset(subset, "green") > 13
                            }
                }.sumOf {  it.match("""Game (\d+): .*""".toRegex()).toInt()}
    }

    override fun part2(input: String): Int {
        return input.splitIgnoreEmpty("\n")
                .sumOf { minBallsFromGame(it, "red") * minBallsFromGame(it, "green") * minBallsFromGame(it, "blue") }
    }

    private fun minBallsFromGame(game: String, colour: String): Int {
        return game
                .splitIgnoreEmpty(";")
                .maxOf { parseBallsFromSubset(it, colour) }
    }

    private fun parseBallsFromSubset(subset: String, colour: String): Int {
        return subset.match("""(\d+) $colour""".toRegex()).toInt()
    }
}