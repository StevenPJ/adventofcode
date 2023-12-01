package com.adventofcode.aoc2022

import spock.lang.Specification

class Day6Spec extends Specification {

    def "part 1 example"() {

        expect:
        new Day6().part1(input) == output

        where:
        input                               | output
        'mjqjpqmgbljsphdztnvjfqwrcgsmlb'    | 7
        'bvwbjplbgvbhsrlpgdmjqwftvncz'      | 5
        'nppdvjthqldpwncqszvftbrmjlhg'      | 6
        'nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg' | 10
        'zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw'  | 11
    }

    def "part 2 example"() {

        expect:
        new Day6().part2(input) == output

        where:
        input                               | output
        'mjqjpqmgbljsphdztnvjfqwrcgsmlb'    | 19
        'bvwbjplbgvbhsrlpgdmjqwftvncz'      | 23
        'nppdvjthqldpwncqszvftbrmjlhg'      | 23
        'nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg' | 29
        'zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw'  | 26
    }

}
