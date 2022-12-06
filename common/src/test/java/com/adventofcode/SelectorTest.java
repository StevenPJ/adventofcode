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

    static class FirstSolution extends Solution {
        public FirstSolution() {
            super(0, 1);
        }
    }

    static class SecondSolution extends Solution {
        public SecondSolution() {
            super(0, 2);
        }
    }

    static class DuplicateSecondSolution extends Solution {
        public DuplicateSecondSolution() {
            super(0, 2);
        }
    }

    static class LatestSolution extends Solution {
        public LatestSolution() {
            super(4000, 2);
        }
    }

}