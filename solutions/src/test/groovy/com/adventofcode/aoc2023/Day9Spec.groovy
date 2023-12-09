package com.adventofcode.aoc2023

import spock.lang.Specification

class Day9Spec extends Specification {

    def example = '''\
    0 3 6 9 12 15
    1 3 6 10 15 21
    10 13 16 21 30 45
    '''.stripIndent()


    def example2 = '''\
    0 -1 -2 -3
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day9().part1(example) == 114
        new Day9().part1(example2) == -4
    }

    def "part 2 example"() {

        expect:
        new Day9().part2(example) == 2
    }
}
