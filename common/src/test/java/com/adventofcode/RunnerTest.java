package com.adventofcode;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;

class RunnerTest {

    Display display = Mockito.mock(Display.class);
    Timer timer = Mockito.mock(Timer.class);
    Runner runner = new Runner(display, timer);

    @Test
    void shouldRunIncompleteSolution() {

        Mockito.when(timer.elapsed())
                .thenReturn(Duration.ofMillis(10))
                .thenReturn(Duration.ofMillis(1));

        runner.runSolutions( () -> Collections.singletonList(new IncompleteSolution()));

        verify(display).print("----- 1992, Day 1 -----");
        verify(display).print("   0ms Part 1: unsolved");
        verify(display).print("   0ms Part 2: unneeded");
    }

    @Test
    void shouldRunCompleteSolution() {

        Mockito.when(timer.elapsed())
                .thenReturn(Duration.ofMillis(10))
                .thenReturn(Duration.ofMillis(1000));

        runner.runSolutions( () -> Collections.singletonList(new CompleteSolution()));

        verify(display).print("----- 1992, Day 10 -----");
        verify(display).print("  10ms Part 1: 100");
        verify(display).print("1000ms Part 2: table");
    }

    @Test
    void shouldRunMultipleSolutions() {

        Mockito.when(timer.elapsed())
                .thenReturn(Duration.ofMillis(10))
                .thenReturn(Duration.ofMillis(1000));

        runner.runSolutions( () -> Arrays.asList(new IncompleteSolution(), new CompleteSolution()));

        verify(display).print("----- 1992, Day 1 -----");
        verify(display).print("   0ms Part 1: unsolved");
        verify(display).print("   0ms Part 2: unneeded");
        verify(display).print("----- 1992, Day 10 -----");
        verify(display).print("  10ms Part 1: 100");
        verify(display).print("1000ms Part 2: table");
    }

    static class IncompleteSolution extends Solution {

        @Override
        public int year() {
            return 1992;
        }

        @Override
        public int day() {
            return 1;
        }

        @Override
        public Object part1() {
            return part1("");
        }

        @Override
        public Object part2() {
            return part2("");
        }
    }

    static class CompleteSolution extends Solution {

        @Override
        public Object part1() {
            return 100;
        }

        @Override
        public Object part2() {
            return "table";
        }

        @Override
        public int year() {
            return 1992;
        }

        @Override
        public int day() {
            return 10;
        }
    }

}