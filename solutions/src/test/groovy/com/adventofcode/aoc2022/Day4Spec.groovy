package com.adventofcode.aoc2022

import spock.lang.Specification

class Day4Spec extends Specification {

    def example = '''\
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
        '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day4().part1(example) == 2
    }

    def "part 2 example"() {

        expect:
        new Day4().part2(example) == 4
    }
}
