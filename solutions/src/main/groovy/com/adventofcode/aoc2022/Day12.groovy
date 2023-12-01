package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day12 extends Solution {

    @Override
    def part1(String input) {
        def grid = input.tokenize().collect { it.toCharArray() }
        new Maze(grid).findShortestPathBetween('S', 'E')
    }

    @Override
    def part2(String input) {
        def grid = input.tokenize().collect { it.toCharArray() }
        def maze = new Maze(grid)
        maze.replace('S', 'a')
        maze.findShortestPathBetween('a', 'E')
    }
}

class Maze {

    List<char[]> grid

    Maze(List<char[]> grid) {
        this.grid = grid.transpose() as List<char[]>
    }

    def replace(String src, String value) {
        findInGrid(src).each {
            grid[it.x][it.y] = value as char
        }
    }

    def findShortestPathBetween(String start, String end) {
        def starts = findInGrid(start)
        def unsettled = [starts[0]]
        def settled = []
        def paths = starts.collectEntries{[(it): 0]}.withDefault { key -> Integer.MAX_VALUE }
        while (!unsettled.isEmpty()) {
            def evaluation = unsettled.pop()
            neighboursOf(evaluation).each {
                if (!settled.contains(it)) {
                    def sourceDistance = paths.get(evaluation)
                    if (sourceDistance + 1 < paths.get(it)) {
                        paths.put(it, sourceDistance + 1)
                    }
                    unsettled.add(it)
                }
            }
            settled << evaluation
            unsettled = unsettled.unique().sort{paths.get(it)}
        }
        return findInGrid(end).collect{paths.get(it)}.min()
    }

    def findInGrid(String character) {
        def width = grid.first().size()
        def occurrences = grid.flatten().join().findIndexValues { it == character}
        occurrences.collect {
            new Vector( (it / width) as int, (it % width) as int )
        }
    }

    def neighboursOf(Vector vector) {
        Vector.directions.values()
                .collect { vector + it}
                .findAll {withinGrid(it) && stepHeight(vector, it) <= 1}
    }

    def stepHeight(Vector src, Vector dst) {
        return getHeight(dst) - getHeight(src)
    }

    def getHeight(Vector vector) {
        def srcChar =  grid[vector.x][vector.y]
        if (srcChar == 'S' as char) return 'a' as char
        if (srcChar == 'E' as char) return 'z' as char
        return srcChar as int
    }

    def withinGrid(Vector vector) {
        return vector.x >= 0
                && vector.x < grid.size()
                && vector.y >= 0
                && vector.y < grid.first().size()
    }
}