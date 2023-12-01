package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day1 extends Solution {

    @Override
    def part1(String input) {
        totalCaloriesByElf(input).max()
    }

    @Override
    def part2(String input) {
        totalCaloriesByElf(input).sort{-it}[0..2].sum()
    }

    static totalCaloriesByElf(String input) {
        input.split('\n\n')
                .collect {
                    it.split('\n')
                            .collect { it as Long }
                            .sum()
                }
    }
}
