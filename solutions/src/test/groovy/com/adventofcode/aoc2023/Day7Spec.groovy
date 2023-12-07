package com.adventofcode.aoc2023

import spock.lang.Specification

class Day7Spec extends Specification {

    def example = '''\
    32T3K 765
    T55J5 684
    KK677 28
    KTJJT 220
    QQQJA 483
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day7().part1(example) == 6440
    }

    def "part 2 example"() {

        expect:
        new Day7().part2(example) == 5905
    }
}
