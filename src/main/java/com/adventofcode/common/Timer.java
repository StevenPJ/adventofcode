package com.adventofcode.common;

import java.time.Duration;
import java.time.Instant;

class Timer {

    Instant start;

    public Duration elapsed() {
        var start = this.start;
        start();
        return Duration.between(start, Instant.now());
    }

    public void start() {
        this.start = Instant.now();
    }
}
