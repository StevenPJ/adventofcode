package com.adventofcode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SelectorTest {

    @Test
    void shouldThrowIfSolutionNotAvailable() {

        var noSolutionExistsOnDay = new Selector.One(2022, 32); // there is no day 32 in aoc

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(noSolutionExistsOnDay::solutions);
    }

    @Test
    void shouldReturnAllSolutionsInOrder() {
        assertThat(new Selector.All().solutions())
                .containsSequence(new FirstSolution(), new SecondSolution());
    }

    @Test
    void shouldReturnSingleSolution() {
        var testSolution = new Selector.One(0, 1);

        assertThat(testSolution.solutions()).containsExactly(new FirstSolution());
    }

    @Test
    void shouldThrowWhenADuplicateSolutionExists() {
        var testSolution = new Selector.One(0, 2);

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(testSolution::solutions);
    }

    @Test
    void shouldReturnLatest() {
        var testSolution = new Selector.Last();

        assertThat(testSolution.solutions()).containsExactly(new LatestSolution());
    }

    @Test
    void shouldReturnAllSolutionsInYearInOrder() {
        assertThat(new Selector.Year(4000).solutions())
                .containsSequence(new LatestSolution(), new SecondLatestSolution());
    }

    static class FirstSolution extends Solution {

        @Override
        public int year() {
            return 0;
        }

        @Override
        public int day() {
            return 1;
        }
    }

    static class SecondSolution extends Solution {
        @Override
        public int year() {
            return 0;
        }

        @Override
        public int day() {
            return 2;
        }
    }

    static class DuplicateSecondSolution extends Solution {
        @Override
        public int year() {
            return 0;
        }

        @Override
        public int day() {
            return 2;
        }
    }

    static class LatestSolution extends Solution {
        @Override
        public int year() {
            return 4000;
        }

        @Override
        public int day() {
            return 2;
        }
    }

    static class SecondLatestSolution extends Solution {
        @Override
        public int year() {
            return 4000;
        }

        @Override
        public int day() {
            return 1;
        }
    }

}