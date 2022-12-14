package com.adventofcode.aoc2022

import spock.lang.Specification

class Day14Spec extends Specification {

    def example = '''\
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day14().part1(example) == 24
    }

    def "part 2 example"() {

        expect:
        new Day14().part2(example) == 93
    }

}
