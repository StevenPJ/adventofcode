package com.adventofcode.aoc2023

import spock.lang.Specification

class Day1Spec extends Specification {

    def example = '''\
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day1().part1(example) == 142
    }


    def example2 = '''\
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    '''.stripIndent()

    def "part 2 example"() {

        expect:
        new Day1().part2(example2) == 281
    }
}
