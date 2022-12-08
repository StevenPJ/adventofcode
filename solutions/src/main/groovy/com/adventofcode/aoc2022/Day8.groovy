package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day8 extends Solution {


    @Override
    def part1(String input) {
        getGridViewingAngles(input)
                .collect { getVisible(it) }
                .flatten()
                .unique()
                .size()
    }

    @Override
    def part2(String input) {
        def lines = getGridViewingAngles(input)
        lines.flatten()
                .collect { scoreVisibility(lines, it as Tree) }
                .max()
    }

    static getGridViewingAngles(String input) {
        def leftToRight = input.tokenize()
                .collect { it.collect { new Tree(Integer.valueOf(it)) } }
        def rightToLeft = leftToRight.collect { it.reverse() }
        def topDown = leftToRight.transpose() as List<List<Tree>>
        def bottomUp = topDown.collect { it.reverse() }
        return leftToRight + rightToLeft + topDown + bottomUp
    }

    static scoreVisibility(List<List<Tree>> lines, Tree tree) {
        lines.findAll { it.contains(tree) }
                .collect {
                    def candidates = it.drop(it.indexOf(tree) + 1)
                    def lastVisibleTreeIndex = candidates.findIndexOf { it.height >= tree.height }
                    return lastVisibleTreeIndex < 0 ? candidates.size() : lastVisibleTreeIndex + 1
                }.inject(1, { a, b -> a * b })
    }

    static getVisible(List<Tree> row) {
        row.inject([], { visible, tree ->
            tree.isTallerThan(visible) ? visible + tree : visible
        })
    }

    static class Tree {
        int height

        Tree(int height) {
            this.height = height
        }

        boolean isTallerThan(List<Tree> trees) {
            return trees.isEmpty()
                    || height > trees.collect { it.height }.max()
        }
    }
}
