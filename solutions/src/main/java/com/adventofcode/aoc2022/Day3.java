package com.adventofcode.aoc2022;

import com.adventofcode.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day3 extends Solution {

    public Day3() {
        super(2022, 3);
    }

    List<String> priorities = Arrays.asList(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );

    @Override
    public Object part1(String input) {
        return Stream.of(input.split("\n"))
                .map(rucksackContents -> {
                    int mid = rucksackContents.length() / 2;
                    String[] parts = {rucksackContents.substring(0, mid), rucksackContents.substring(mid)};
                    for (int i=0; i<mid; i++) {
                        String itemUnderTest = String.valueOf(parts[0].charAt(i));
                        if (parts[1].contains(itemUnderTest))
                            return priorities.indexOf(itemUnderTest) + 1;
                    }
                    return 0;
                })
                .reduce(0, Integer::sum);
    }

    @Override
    public Object part2(String input) {
        var rucksacks = input.split("\n");
        var total = 0;
        for (int i=0; i<rucksacks.length; i+=3) {
            var bagItems1 = rucksacks[i].chars().boxed().collect(Collectors.toSet());
            var bagItems2 = rucksacks[i+1].chars().boxed().collect(Collectors.toSet());
            var bagItems3 = rucksacks[i+2].chars().boxed().collect(Collectors.toSet());
            bagItems1.retainAll(bagItems2);
            bagItems1.retainAll(bagItems3);
            for (int item : bagItems1) {
                total += priorities.indexOf(String.valueOf((char) item)) + 1;
            }
        }
        return total;
    }
}
