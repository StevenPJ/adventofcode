package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.splitIgnoreEmpty

class Day2 : Solution() {

    override fun part1(input: String): Int {
        return input.splitIgnoreEmpty("\n")
                .filter {
                    it.drop(it.indexOf(":") + 1 )
                            .splitIgnoreEmpty(";")
                            .none { subset ->
                                parseBallsFromSubset(subset, "red") > 12
                                        || parseBallsFromSubset(subset, "blue") > 14
                                        || parseBallsFromSubset(subset, "green") > 13
                            }
                }
                .map {
                    val regex = """Game (\d+): .*""".toRegex()
                    val matchResult = regex.find(it)
                    val (game) = matchResult!!.destructured
                    game.toInt()
                }
                .sum()
    }

    override fun part2(input: String): Int {
        return input.splitIgnoreEmpty("\n")
                .map { minBallsFromGame(it, "red") * minBallsFromGame(it, "green") * minBallsFromGame(it, "blue") }
                .sum()
    }

    private fun minBallsFromGame(game: String, colour: String): Int {
        return game.drop(game.indexOf(":") + 1 )
                .splitIgnoreEmpty(";")
                .map{parseBallsFromSubset(it, colour)}
                .max()
    }


    private fun parseBallsFromSubset(subset: String, colour: String): Int {
        val regex = """(\d+) ${colour}""".toRegex()
        val matchResult = regex.find(subset) ?: return 0
        val (number) = matchResult.destructured
        return number.toInt()
    }

}