package com.adventofcode.aoc2023

import spock.lang.Specification

class Day3Spec extends Specification {

    def example = '''\
    467..114..
    ...*......
    ..35..633.
    ......#...
    617*......
    .....+.58.
    ..592.....
    ......755.
    ...$.*....
    .664.598..
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day3().part1(example) == 4361
    }

    def "part 2 example"() {

        expect:
        new Day3().part2(example) == 467835
    }
}
