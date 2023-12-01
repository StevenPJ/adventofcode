package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day6 extends Solution {


    @Override
    def part1(String input) {
        getFirstInstanceOfUniqueCharacters(input, 4)
    }

    @Override
    def part2(String input) {
        getFirstInstanceOfUniqueCharacters(input, 14)
    }

    static getFirstInstanceOfUniqueCharacters(String input, int setSize) {
        def firstUniqueSet = (input as List)
                .collate( setSize, 1, false)
                .collect{ it as Set }
                .find{ it.size() == setSize}
        input.indexOf(firstUniqueSet.join()) + setSize
    }
}
