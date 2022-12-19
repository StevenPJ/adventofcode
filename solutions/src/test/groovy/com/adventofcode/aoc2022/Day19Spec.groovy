package com.adventofcode.aoc2022

import spock.lang.Specification

class Day19Spec extends Specification {

    def example = '''\
    Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
    Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day19().part1(example) == 33
    }

    def "part 2 example"() {

        expect:
        new Day19().part2(example) == 62 * 56
    }
}

