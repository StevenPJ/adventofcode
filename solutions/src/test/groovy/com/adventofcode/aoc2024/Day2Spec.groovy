package com.adventofcode.aoc2024

import spock.lang.Specification

class Day2Spec extends Specification {

    def example = '''\
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day2().part1(example) == 2
    }


    def "part 2 example"() {

        expect:
        new Day2().part2(example) == 4
    }

    def example2 = '''\
    45 47 48 51 54 56 54
    '''.stripIndent()

    def "part 2 example 2"() {

        expect:
        new Day2().part2(example2) == 1
    }
}
