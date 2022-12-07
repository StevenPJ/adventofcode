package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day5 extends Solution {


    @Override
    def part1(String input) {
        def stacks, procedures
        (stacks, procedures) = prepareInput(input)
        procedures.each{ procedure ->
            procedure[0].times {
                stacks[procedure[2]].add(stacks[procedure[1]].removeLast())
            }
        }
        return stacks.collect { it.last() }.join('')
    }

    @Override
    def part2(String input) {
        def stacks, procedures
        (stacks, procedures) = prepareInput(input)
        procedures.each{ procedure ->
            def moved = []
            procedure[0].times {
                moved.push(stacks[procedure[1]].removeLast())
            }
            stacks[procedure[2]].addAll(moved)
        }
        return stacks.collect { it.last() }.join('')
    }

    static prepareInput(String input) {
        def stacksAndProcedure = input.split("\n\n")
        def stacks = parseStacks(stacksAndProcedure[0])
        def procedures = parseProcedure(stacksAndProcedure[1])
        return [stacks, procedures]
    }

    static cleanLine(String stackLine) {
        stackLine
                .replaceAll(/\s\s\s\s/, "[0]")
                .replaceAll(/\s/, "")
                .replaceAll(/\[/, "")
                .replaceAll(/\]/, "")
                .toCharArray() as List
    }

    static parseStacks(String stacksInput) {
        return stacksInput.split('\n')
                .findAll{ it.contains("[")}
                .collect { cleanLine(it) }
                .reverse()
                .transpose()
                .collect { it.findAll{ it != "0"} }
    }

    static parseProcedure(String procedureInput) {
        procedureInput
                .replaceAll(/[a-zA-Z]/, "")
                .tokenize()
                .collect { it as int}
                .collate(3)
                .collect{ [ it[0], it[1] -1, it[2] -1 ]}
    }
}
