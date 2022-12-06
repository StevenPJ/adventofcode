package com.adventofcode;

import java.time.Duration;
import java.time.Instant;

class Timer {

    Instant start;

    public Duration elapsed() {
        return Duration.between(this.start, Instant.now());
    }

    public void start() {
        this.start = Instant.now();
    }
}
