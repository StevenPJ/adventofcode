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
            try {
                timer.start();
                var answer = solution.part1();
                display.print(format("%6s Part 1: %s", timer.elapsed(), answer));
            } catch (UnsupportedOperationException ex) {
                display.print(format("%4dms Part 1: %s", 0, "unsolved"));
            }
            try {
                timer.start();
                var answer = solution.part2();
                display.print(format("%6s Part 2: %s", timer.elapsed(), answer));
            } catch (UnsupportedOperationException ex) {
                display.print(format("%4dms Part 2: %s", 0, "unneeded"));
            }
        });
    }
}
