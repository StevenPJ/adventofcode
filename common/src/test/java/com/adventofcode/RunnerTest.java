package com.adventofcode;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
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
                .thenReturn(Duration.ofMillis(10));

        runner.runSolutions( () -> Collections.singletonList(new IncompleteSolution()));

        verify(display).print("----- 1992, Day 1 -----");
        verify(display).print("   0ms Part 1: unsolved");
    }

    @Test
    void shouldRunCompleteSolution() {

        Mockito.when(timer.elapsed())
                .thenReturn(Duration.ofMillis(10));

        runner.runSolutions( () -> Collections.singletonList(new CompleteSolution()));

        verify(display).print("----- 1992, Day 10 -----");
        verify(display).print("  10ms Part 1: 100");
    }

    @Test
    void shouldRunMultipleSolutions() {

        Mockito.when(timer.elapsed())
                .thenReturn(Duration.ofMillis(0))
                .thenReturn(Duration.ofMillis(1000));

        runner.runSolutions( () -> Arrays.asList(new IncompleteSolution(), new CompleteSolution()));

        verify(display).print("----- 1992, Day 1 -----");
        verify(display).print("   0ms Part 1: unsolved");
        verify(display).print("----- 1992, Day 10 -----");
        verify(display).print("1000ms Part 1: 100");
    }

    static class IncompleteSolution extends Solution {

        public IncompleteSolution() {
            super(1992, 1);
        }

        @Override
        public Object part1() {
            return part1("test");
        }
    }

    static class CompleteSolution extends Solution {

        public CompleteSolution() {
            super(1992, 10);
        }

        @Override
        public Object part1() {
            return 100;
        }
    }

}