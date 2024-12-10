package com.adventofcode.aoc2024

import spock.lang.Specification

class Day1Spec extends Specification {

    def example = '''\
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day1().part1(example) == 11
    }

    def "part 2 example"() {

        expect:
        new Day1().part2(example) == 31
    }
}
