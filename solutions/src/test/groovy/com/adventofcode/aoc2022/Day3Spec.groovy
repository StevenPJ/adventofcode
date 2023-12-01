package com.adventofcode.aoc2022

import spock.lang.Specification

class Day3Spec extends Specification {

    def example = '''\
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
        '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day3().part1(example) == 157
    }

    def "part 2 example"() {

        expect:
        new Day3().part2(example) == 70
    }
}
