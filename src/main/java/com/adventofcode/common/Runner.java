package com.adventofcode.common;

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
            display.print(format("%4dms Part 1: %s", timer.elapsed().toMillis(), solution.part1()));
            display.print(format("%4dms Part 2: %s", timer.elapsed().toMillis(), solution.part2()));
        });
    }
}
