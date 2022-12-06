package com.adventofcode;

import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@RequiredArgsConstructor
class Runner {
    private final Display display;
    private final Timer timer;

    public void runSolutions(Selector which) {
        which.solutions().forEach( solution -> {
            display.print(format("----- %d, Day %d -----", solution.year(), solution.day()));
            timer.start();
            try {
                display.print(format("%4dms Part 1: %s", timer.elapsed().toMillis(), solution.part1()));
            } catch (UnsupportedOperationException ex) {
                display.print(format("%4dms Part 1: %s", 0, "unsolved"));
            }
        });
    }
}
