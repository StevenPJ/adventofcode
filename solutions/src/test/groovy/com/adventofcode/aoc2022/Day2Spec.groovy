package com.adventofcode.aoc2022

import spock.lang.Specification

class Day2Spec extends Specification {

    def example = '''\
    A Y
    B X
    C Z
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day2().part1(example) == 15
    }

    def "part 2 example"() {

        expect:
        new Day2().part2(example) == 12
    }
}
