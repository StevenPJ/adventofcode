package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty

class Day1 : Solution() {

    override fun part1(input: String): Long {
        return input.toNumbers().sumOf { it.toDigits().firstAndLast().toNumber() }
    }

    // handle overlapping numbers by leaving the number behind for the next one.
    override fun part2(input: String): Long {
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