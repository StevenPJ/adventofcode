package com.adventofcode.aoc2022;

import com.adventofcode.Solution;

import java.util.Map;
import java.util.stream.Stream;


public class Day2 extends Solution {

    @Override
    public Object part1(String input) {
        var matchScores = Map.of(
                "A X", 4, "A Y", 8, "A Z", 3,
                "B X", 1, "B Y", 5, "B Z", 9,
                "C X", 7, "C Y", 2, "C Z", 6
                );
        return Stream.of(input.split("\n"))
                .map(matchScores::get)
                .reduce(0, Integer::sum);
    }

    @Override
    public Object part2(String input) {
        var matchScores = Map.of(
                "A X", 3, "A Y", 4, "A Z", 8,
                "B X", 1, "B Y", 5, "B Z", 9,
                "C X", 2, "C Y", 6, "C Z", 7
        );
        return Stream.of(input.split("\n"))
                .map(matchScores::get)
                .reduce(0, Integer::sum);
    }
}
