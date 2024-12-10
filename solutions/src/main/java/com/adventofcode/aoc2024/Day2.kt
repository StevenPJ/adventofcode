package com.adventofcode.aoc2024

import com.adventofcode.Solution
import com.adventofcode.util.*
import kotlin.math.abs

class Day2 : Solution() {

    private fun safe(report: List<Int>) : Boolean {
        var lastValue : Int? = null
        var isIncreasing : Boolean? = null
        for (level in report) {
            if (lastValue == null) lastValue = level
            else {
                if (level == lastValue) return false
                if (isIncreasing == null) isIncreasing = level > lastValue
                if (isIncreasing && level < lastValue) return false
                if (!isIncreasing && level > lastValue) return false
                if (abs(level - lastValue) > 3) return false
                lastValue = level
            }
        }
        return true
    }

    override fun part1(input: String): Int {
        return input.nonEmptyLines()
            .map { report -> report.split(" ").map { it.toInt() } }
            .count { safe(it) }
    }

    override fun part2(input: String): Int {
        return input.nonEmptyLines()
            .map { report -> report.split(" ").map { it.toInt() } }
            .count { report ->
                val tolerantReports = sequence {
                    report.forEachIndexed { idx, _ ->
                        yield(report.filterIndexed{i, _ -> i != idx })
                    }
                }
                tolerantReports.any { safe(it) }
            }
    }
}