package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day4 extends Solution {


    @Override
    def part1(String input) {
        assignments(input).count {
            it[0].containsAll(it[1]) || it[1].containsAll(it[0])
        }
    }

    @Override
    def part2(String input) {
        assignments(input).count {
            !it[0].intersect(it[1]).isEmpty()
        }
    }

    static assignments(String input) {
        input.tokenize().collect{ pair ->
            def assignments = pair.split(',')
            def firstElfAssignments = section(assignments[0])
            def secondElfAssignments = section(assignments[1])
            return [firstElfAssignments, secondElfAssignments]
        }
    }

    static section(String assignment) {
        def sections = assignment.split("-")
        return (sections[0] as Long)..(sections[1] as Long) as Set
    }

}
