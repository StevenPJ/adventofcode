package com.adventofcode.aoc2022

import spock.lang.Specification

class Day8Spec extends Specification {

    def example = '''\
    30373
    25512
    65332
    33549
    35390
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day8().part1(example) == 21
    }


    def "part 2 example"() {

        expect:
        new Day8().part2(example) == 8
    }

}
