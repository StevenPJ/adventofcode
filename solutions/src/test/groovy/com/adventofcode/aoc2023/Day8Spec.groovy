package com.adventofcode.aoc2023

import spock.lang.Specification

class Day8Spec extends Specification {

    def example = '''\
    RL
    
    AAA = (BBB, CCC)
    BBB = (DDD, EEE)
    CCC = (ZZZ, GGG)
    DDD = (DDD, DDD)
    EEE = (EEE, EEE)
    GGG = (GGG, GGG)
    ZZZ = (ZZZ, ZZZ)
    '''.stripIndent()

    def example2 = '''\
    LLR
    
    AAA = (BBB, BBB)
    BBB = (AAA, ZZZ)
    ZZZ = (ZZZ, ZZZ)
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day8().part1(example) == 2
        new Day8().part1(example2) == 6
    }

    def example3 = '''\
    LR

    11A = (11B, XXX)
    11B = (XXX, 11Z)
    11Z = (11B, XXX)
    22A = (22B, XXX)
    22B = (22C, 22C)
    22C = (22Z, 22Z)
    22Z = (22B, 22B)
    XXX = (XXX, XXX)
    '''.stripIndent()


    def "part 2 example"() {

        expect:
        new Day8().part2(example3) == 6
    }
}
