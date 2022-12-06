package com.adventofcode.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    @Test
    public void part1Example() {
        assertThat(new Day1().part1("""
                1000
                2000
                3000
                                
                4000
                                
                5000
                6000
                                
                7000
                8000
                9000
                                
                10000
                """)).isEqualTo(24000);
    }

    @Test
    public void part2Example() {
        assertThat(new Day1().part2("""
                1000
                2000
                3000
                                
                4000
                                
                5000
                6000
                                
                7000
                8000
                9000
                                
                10000
                """)).isEqualTo(45000);
    }

}