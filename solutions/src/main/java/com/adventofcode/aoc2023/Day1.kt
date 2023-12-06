package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty

class Day1 : Solution() {

    override fun part1(input: String): Int {
        return input.nonEmptyLines()
                .map { it.filter { it.isDigit() } }
                .map { it.toCharArray() }
                .map { String.format("%s%s", it.first(), it.last())}
                .map { it.toInt() }
                .sum()
    }

    // handle overlapping numbers by leaving the number behind for the next one.
    override fun part2(input: String): Int {
        return part1(input
                .replace("one", "one1one")
                .replace("two", "two2two")
                .replace("three", "three3three")
                .replace("four", "four4four")
                .replace("five", "five5five")
                .replace("six", "six6six")
                .replace("seven", "seven7seven")
                .replace("eight", "eight8eight")
                .replace("nine", "nine9nine")
        )
    }

}