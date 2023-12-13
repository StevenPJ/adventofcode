package com.adventofcode.aoc2023

import spock.lang.Specification

class Day13Spec extends Specification {

    def example = '''\
    #.##..##.
    ..#.##.#.
    ##......#
    ##......#
    ..#.##.#.
    ..##..##.
    #.#.##.#.
    
    #...##..#
    #....#..#
    ..##..###
    #####.##.
    #####.##.
    ..##..###
    #....#..#
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day13().part1(example) == 405
    }

    def "part 2 example"() {

        expect:
        new Day13().part2(example) == 400
    }
}
