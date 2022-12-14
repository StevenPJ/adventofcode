package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.json.JsonSlurper

class Day14 extends Solution {

    static def final SOURCE = new Vector(500, 0)

    @Override
    def part1(String input) {
        def cave = parseCave(input)
        def nRocks = cave.size()
        def floor = cave.y.max()
        def previous = []
        def stack = [SOURCE]
        while(previous != cave) {
            previous = cave
            cave = simulateSandFall(cave, floor, stack)
        }
        return cave.size() - nRocks
    }

    @Override
    def part2(String input) {
        def cave = parseCave(input)
        def floor = getFloor(cave)
        cave += floor
        def nRocks = cave.size()
        def stack = [SOURCE]
        while (cave.first() != SOURCE) {
            cave = simulateSandFall(cave, floor.y.max(), stack)
        }
        return cave.size() - nRocks
    }

    static List<Vector> parseCave(String input) {
        input.split("\n").collect { path ->
            path.split(" -> ")
                    .collect { new JsonSlurper().parseText("[$it]") as List<Integer> }
                    .collate(2, 1, false)
                    .collect {
                        [(it[0][0]..it[1][0]), (it[0][1]..it[1][1])]
                                .combinations()
                                .collect { new Vector(it[0], it[1]) }
                    }.flatten()
        }.flatten().unique()
    }

    static def getFloor(List<Vector> cave) {
        def floorY = cave.collect { it.y }.max() + 2
        [(0..1000), (floorY..floorY)]
                .combinations()
                .collect { new Vector(it[0], it[1]) }
    }

    List<Vector> simulateSandFall(List<Vector> cave, int floor, List<Vector> sources) {
        def source = sources.pop()
        def nextStep = nextSteps(source).find{!cave.contains(it)}
        if (nextStep == null) {
            return [source] + cave
        }
        if (nextStep.y >= floor) {
            return cave
        }
        sources.push(source)
        sources.push(nextStep)
        return simulateSandFall(cave, floor, sources)
    }

    static def nextSteps(Vector sand) {
        return [
                sand + Vector.directions.U,
                sand + Vector.directions.U + Vector.directions.L,
                sand + Vector.directions.U + Vector.directions.R
        ]
    }
}