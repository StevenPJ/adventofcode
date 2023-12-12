package com.adventofcode.aoc2023

import spock.lang.Specification

class Day12Spec extends Specification {

    def example = '''\
    ???.### 1,1,3
    .??..??...?##. 1,1,3
    ?#?#?#?#?#?#?#? 1,3,1,6
    ????.#...#... 4,1,1
    ????.######..#####. 1,6,5
    ?###???????? 3,2,1
    '''.stripIndent()


    def "part 1 example"() {

        expect:
        new Day12().part1(example) == 21
    }

    def "part 2 example"() {

        expect:
        new Day12().part2(example) == 525152
    }
}
