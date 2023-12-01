package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.json.JsonSlurper

class Day14 extends Solution {

    static def final SOURCE = new Vector(500, 0)

    @Override
    def part1(String input) {
        def cave = parseCave(input)
        def nRocks = cave.size()
        List<Vector> crawler = [SOURCE]
        while(crawler.first().y <= cave.y.max()) {
            def source = crawler.first()
            def next = nextSteps(source).find{!cave.contains(it)}
            if (next == null) {
                cave << crawler.pop()
            } else {
                crawler.push(next)
            }
        }
        return cave.size() - nRocks
    }

    @Override
    def part2(String input) {
        def cave = parseCave(input)
        def nRocks = cave.size()
        def floor = cave.y.max() + 2
        List<Vector> crawler = [SOURCE]
        while(!cave.contains(SOURCE)) {
            def source = crawler.first()
            def next = nextSteps(source).find{!cave.contains(it)}
            if (next == null || next.y >= floor) {
                cave << crawler.pop()
            } else {
                crawler.push(next)
            }
        }
        return cave.size() - nRocks
    }

    static Set<Vector> parseCave(String input) {
        input.split("\n").collect { path ->
            path.split(" -> ")
                    .collect { new JsonSlurper().parseText("[$it]") as List<Integer> }
                    .collate(2, 1, false)
                    .collect {
                        [(it[0][0]..it[1][0]), (it[0][1]..it[1][1])]
                                .combinations()
                                .collect { new Vector(it[0], it[1]) }
                    }.flatten()
        }.flatten().unique() as HashSet
    }

    static def nextSteps(Vector sand) {
        return [
                sand + Vector.directions.U,
                sand + Vector.directions.U + Vector.directions.L,
                sand + Vector.directions.U + Vector.directions.R
        ]
    }
}