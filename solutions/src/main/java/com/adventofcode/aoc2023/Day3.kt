package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines

class Day3 : Solution() {

    private val nonSymbols = "[A-Za-z0-9.]"
    private val nonGearCandidates = "[^*]"

    override fun part1(input: String): Int {
        val lines = input.nonEmptyLines()
        return getAllCoordinatesWhenExcluding(nonSymbols, lines)
                .flatMap{ symbol -> getNeighbouringPartNumbers(symbol, lines) }
                .toSet()
                .sumOf { it.partNumber }
    }

    override fun part2(input: String): Int {
        val lines = input.nonEmptyLines()
        return getAllCoordinatesWhenExcluding(nonGearCandidates, lines)
                .map { gearCandidate -> getNeighbouringPartNumbers(gearCandidate, lines) }
                .filter { it.size == 2 } // gears are adjacent to exactly 2 part numbers
                .sumOf { gearRatios -> gearRatios.map { it.partNumber }.reduce(Int::times) }

    }

    private fun getAllCoordinatesWhenExcluding(exclusionPattern: String, lines: List<String>, ): List<Point> {
        val symbols = mutableListOf<Point>()
        lines
                .map { Regex(exclusionPattern).replace(it," ") }
                .forEachIndexed{ colIdx, col ->
                    col.toCharArray().forEachIndexed { rowIdx, row ->
                        if (!row.isWhitespace())
                            symbols.add(Point(rowIdx, colIdx))
                    }}
        return symbols
    }

    // check points adjacent to input, if it's a digit, its part of a partNumber
    private fun getNeighbouringPartNumbers(point: Point, lines: List<String>): Set<PartNumber> {
        val grid = Dimensions(lines)
        return point.neighboursWithin(grid)
                .filter { lines[it.y][it.x].isDigit() }
                .map { it.getPartNumber(lines, grid) }
                .toSet() // remove duplicates
    }

    // assuming the current point is part of a partNumber
    // get the start of the part and extract the value and start coordinate
    // the coordinate will help us root out duplicates
    private fun Point.getPartNumber(input: List<String>, grid: Dimensions): PartNumber {
        val previousPoint = this.left()
        if (grid.contains(previousPoint) && input.charAt(previousPoint).isDigit())
            return previousPoint.getPartNumber(input, grid)
        val (partNumber) = Regex("(\\d+)").find(input[this.y].substring(this.x))!!.destructured
        return PartNumber(this, partNumber.toInt())
    }

    private data class PartNumber(val coordinate: Point, val partNumber: Int)
}