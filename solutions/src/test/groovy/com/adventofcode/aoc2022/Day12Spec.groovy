package com.adventofcode.aoc2022

import spock.lang.Specification

class Day12Spec extends Specification {

    def example = '''\
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day12().part1(example) == 31
    }

    def "part 2 example"() {

        expect:
        new Day12().part2(example) == 29
    }
}
