package com.adventofcode.aoc2022

import spock.lang.Specification

class Day2Spec extends Specification {

    def "part 1 example"() {

        expect:
        new Day2().part1('''\
        A Y
        B X
        C Z
        '''.stripIndent()) == 15
    }

    def "part 2 example"() {

        expect:
        new Day2().part2('''\
        A Y
        B X
        C Z
        '''.stripIndent()) == 12
    }
}
