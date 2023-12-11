package com.adventofcode.aoc2023

import spock.lang.Specification

class Day11Spec extends Specification {

    def example = '''\
    ...#......
    .......#..
    #.........
    ..........
    ......#...
    .#........
    .........#
    ..........
    .......#..
    #...#.....
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day11().part1(example) == 374
    }

    def "part 2 example"() {

        expect:
        new Day11().part2(example) == 82000210
    }
}
