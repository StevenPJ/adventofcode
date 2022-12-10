package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day10 extends Solution {

    @Override
    def part1(String input) {
        def x = getXRegister(input)
        [20, 60, 100, 140, 180, 220].collect{ x[it-1] * it}.sum()
    }

    @Override
    def part2(String input) {
        def x = getXRegister(input)
        def crt = []
        x.eachWithIndex { int entry, int cycle ->
            if ([entry-1, entry, entry+1].contains(cycle % 40))
                crt << '#'
            else
                crt << '.'
        }
        crt.removeLast()
        return crt.collate(40).collect {it.join()}.join("\n")
    }

    static getXRegister(String input) {
        def x = [1]
        def lastCommand = null
        input.tokenize().eachWithIndex{ String command, int cycle ->
            if (lastCommand == 'addx')
                x << x.last() + Integer.parseInt(command)
            else
                x << x.last()
            lastCommand = command
        }
        return x
    }

}