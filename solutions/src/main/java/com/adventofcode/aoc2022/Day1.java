package com.adventofcode.aoc2022;


import com.adventofcode.Solution;

import java.util.Collections;
import java.util.stream.Stream;

public class Day1 extends Solution {

    @Override
    public Object part1(String input) {
        return Stream.of(input.split("\n\n"))
                .map(calories -> Stream.of(calories.split("\n"))
                    .map(Integer::valueOf)
                    .reduce(0, Integer::sum))
                .max(Integer::compareTo)
                .orElseThrow();
    }

    @Override
    public Object part2(String input) {
        return Stream.of(input.split("\n\n"))
                .map(calories -> Stream.of(calories.split("\n"))
                        .map(Integer::valueOf)
                        .reduce(0, Integer::sum))
                .sorted(Integer::compareTo)
                .sorted(Collections.reverseOrder())
                .limit(3)
                .reduce(0, Integer::sum);
    }
}
