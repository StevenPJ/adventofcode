package com.adventofcode.aoc2022

import com.adventofcode.Solution

class Day2 extends Solution {

    @Override
    def part1(String input) {
        scoreGames(input, [
                "A X": 4, "A Y": 8, "A Z": 3,
                "B X": 1, "B Y": 5, "B Z": 9,
                "C X": 7, "C Y": 2, "C Z": 6
        ])
    }

    @Override
    def part2(String input) {
        scoreGames(input, [
                "A X": 3, "A Y": 4, "A Z": 8,
                "B X": 1, "B Y": 5, "B Z": 9,
                "C X": 2, "C Y": 6, "C Z": 7
        ])
    }

    static scoreGames(String input, Map<String, Integer> scoreSheet) {
        input.split("\n").collect{ scoreSheet.get(it)}.sum()
    }
}
