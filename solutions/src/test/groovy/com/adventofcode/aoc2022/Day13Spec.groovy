package com.adventofcode.aoc2022

import spock.lang.Specification

class Day13Spec extends Specification {

    def example = '''\
    [1,1,3,1,1]
    [1,1,5,1,1]
    
    [[1],[2,3,4]]
    [[1],4]
    
    [9]
    [[8,7,6]]
    
    [[4,4],4,4]
    [[4,4],4,4,4]
    
    [7,7,7,7]
    [7,7,7]
    
    []
    [3]
    
    [[[]]]
    [[]]
    
    [1,[2,[3,[4,[5,6,7]]]],8,9]
    [1,[2,[3,[4,[5,6,0]]]],8,9]
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day13().part1(example) == 13
    }

    def "part 2 example"() {

        expect:
        new Day13().part2(example) == 140
    }

}
