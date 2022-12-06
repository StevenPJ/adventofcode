package com.adventofcode.aoc2022;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    @Test
    public void part1Example() {
        assertThat(new Day2().part1("""
                A Y
                B X
                C Z
                """)).isEqualTo(15);
    }

    @Test
    public void part2Example() {
        assertThat(new Day2().part2("""
                A Y
                B X
                C Z
                """)).isEqualTo(12);
    }

}