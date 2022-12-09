package com.adventofcode.aoc2022

import spock.lang.Specification

class Day9Spec extends Specification {

    def example = '''\
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day9().part1(example) == 13
    }

    def example2 = '''\
    R 5
    U 8
    L 8
    D 3
    R 17
    D 10
    L 25
    U 20
    '''.stripIndent()

    def "part 2 example"() {

        expect:
        new Day9().part2(example) == 1
        new Day9().part2(example2) == 36

    }

}
