package com.adventofcode.aoc2022

import spock.lang.Specification

class Day5Spec extends Specification {

    def example = '''\
        |    [D]    
        |[N] [C]    
        |[Z] [M] [P]
        | 1   2   3 
        |
        |move 1 from 2 to 1
        |move 3 from 1 to 3
        |move 2 from 2 to 1
        |move 1 from 1 to 2
        '''.stripMargin()

    def "part 1 example"() {

        expect:
        new Day5().part1(example) == 'CMZ'
    }

    def "part 2 example"() {

        expect:
        new Day5().part2(example) == 'MCD'
    }
}
