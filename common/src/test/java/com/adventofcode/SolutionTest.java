package com.adventofcode;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @SneakyThrows
    @Test
    void shouldDownloadInputs() {
        System.setProperty("INPUT_DIR", this.getClass().getResource("/inputs").toURI().getPath());

        assertThat(new TestSolution().part1()).isEqualTo("Test input");
    }

    static class TestSolution extends Solution {

        @Override
        public int year() {
            return 10;
        }

        @Override
        public int day() {
            return 2;
        }

        @Override
        public Object part1(String input) {
            return input;
        }
    }

}