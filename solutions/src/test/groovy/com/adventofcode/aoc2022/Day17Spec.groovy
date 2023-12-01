package com.adventofcode.aoc2022


import spock.lang.Specification

class Day17Spec extends Specification {

    def example = '>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>'


    def "part 1 example"() {

        expect:
        new Day17().part1(example) == 3068
    }

    def "part 2 example"() {

        expect:
        new Day17().part2(example) == 1514285714288
    }
}

