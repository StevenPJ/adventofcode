package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.json.JsonSlurper


class Day13 extends Solution {

    @Override
    def part1(String input) {
        def packetPairs = input.split("\n\n")
        packetPairs.collect {
            def packets = it.split("\n") as List
            def left = new JsonSlurper().parseText(packets[0])
            def right = new JsonSlurper().parseText(packets[1])
            return compare(left, right)
        }.findIndexValues { it < 0}
        .collect { it + 1}
        .sum()
    }

    @Override
    def part2(String input) {
        def dividers = [ [[2]], [[6]] ]
        def packets = input.split("\n")
                .findAll{!it.allWhitespace}
                .collect {new JsonSlurper().parseText(it)}
        packets += dividers
        packets
                .sort { left, right -> compare(left, right) }
                .findIndexValues { dividers.contains(it) }
                .collect { it + 1}
                .inject(1) { a,b -> a*b }
    }

    static int compare(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer)
            return left <=> right
        if (left instanceof Integer && right instanceof List)
            return compare([left], right)
        if (left instanceof List && right instanceof Integer)
            return compare(left, [right])
        return compareList(left as List, right as List)
    }

    static int compareList(List left, List right) {
        def maxI = [left.size(), right.size()].max()
        for (int i=0; i<maxI; i++) {
            if (right.size() < i + 1)
                return 1
            if (left.size() < i + 1)
                return -1
            def diff = compare(left[i], right[i])
            if (diff != 0)
                return diff
        }
        return 0
    }
}