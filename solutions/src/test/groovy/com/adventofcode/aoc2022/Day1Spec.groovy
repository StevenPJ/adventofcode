package com.adventofcode.aoc2022

import spock.lang.Specification

class Day1Spec extends Specification {

    def example = '''\
        1000
        2000
        3000
    
        4000
    
        5000
        6000
    
        7000
        8000
        9000
    
        10000
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day1().part1(example) == 24000
    }

    def "part 2 example"() {

        expect:
        new Day1().part2(example) == 45000
    }
}
