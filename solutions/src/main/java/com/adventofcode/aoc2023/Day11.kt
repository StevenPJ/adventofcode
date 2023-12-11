package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.eachUnorderedPermutationChooseK
import com.adventofcode.util.nonEmptyLines

class Day11 : Solution() {


    override fun part1(input: String): Int {
        val galaxies = parse(input, 2)

        return galaxies.eachUnorderedPermutationChooseK(2).sumOf {it[0].distance(it[1]) }
    }

    override fun part2(input: String): Long {
        val galaxies = parse(input, 1_000_000)

        return galaxies.eachUnorderedPermutationChooseK(2).sumOf {it[0].distance(it[1]).toLong() }
    }

    private fun parse(input: String, expansionSize: Int): List<Point> {
        val galaxies = input.nonEmptyLines().flatMapIndexed { y, row ->
            row.mapIndexedNotNull{ x, char -> if (char == '#') Point(x,y) else null }
        }
        val dimensions = Dimensions(input.nonEmptyLines())
        val emptyCols = (0 until dimensions.width).mapNotNull{ row ->  if (galaxies.none{it.x == row}) row else null }
        val emptyRows = (0 until dimensions.height).mapNotNull{ col ->  if (galaxies.none{it.y == col}) col else null }

        return galaxies.map{
            val emptyRowsAbove = emptyRows.filter { row -> row < it.y }
            val emptyColsLeft = emptyCols.filter { col -> col < it.x }
            Point(it.x + (emptyColsLeft.size * (expansionSize - 1)), it.y + (emptyRowsAbove.size * (expansionSize - 1)))
        }
    }
}