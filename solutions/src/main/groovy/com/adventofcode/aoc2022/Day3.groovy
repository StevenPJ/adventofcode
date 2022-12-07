package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day3 extends Solution {

    def priorities = [
            "-", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    ]

    @Override
    def part1(String input) {
        def rucksackCompartments = input.tokenize().collect{ [ it[0..it.length()/2], it[it.length()/2..-1] ]}
        rucksackCompartments.collect{compartments -> compartments[0].toCharArray()
                .find{compartments[1].contains(it as String)}
                .collect{ priorities.indexOf(it as String)}
                .sum()
        }.sum()
    }

    @Override
    def part2(String input) {
        def rucksacks = input.tokenize()
        def total = 0
        0.step rucksacks.size(), 3, {
            var bagItems1 = rucksacks[it].toCharArray().toSet()
            var bagItems2 = rucksacks[it + 1].toCharArray().toSet()
            var bagItems3 = rucksacks[it + 2].toCharArray().toSet()
            bagItems1.intersect(bagItems2).intersect(bagItems3).forEach {
                total += priorities.indexOf(it as String)
            }
        }
        return total
    }
}
