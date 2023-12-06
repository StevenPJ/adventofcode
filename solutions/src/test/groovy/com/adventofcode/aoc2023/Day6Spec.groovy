package com.adventofcode.aoc2023

import spock.lang.Specification

class Day6Spec extends Specification {

    def example = '''\
    Time:      7  15   30
    Distance:  9  40  200
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day6().part1(example) == 288
    }

    def "part 2 example"() {

        expect:
        new Day6().part2(example) == 71503
    }
}
