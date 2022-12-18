package com.adventofcode.aoc2022


import spock.lang.Specification

class Day18Spec extends Specification {

    def example = '''\
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day18().part1(example) == 64
    }

    def "part 2 example"() {

        expect:
        new Day18().part2(example) == 58
    }
}

