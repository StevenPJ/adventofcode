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
        getXRegister(input).dropRight(1).withIndex().inject('') { result, it ->
            def positionInRow = it.last() % 40
            def containsSprite = ( it.first()-1..it.first()+1 ).contains(positionInRow)
            result.concat(positionInRow == 0 ? '\n' : '').concat(containsSprite ? '#' : '.')
        }
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