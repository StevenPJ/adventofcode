package com.adventofcode;

import java.time.Duration;
import java.time.Instant;

class Timer {

    Instant start;

    public String elapsed() {
        var duration =  Duration.between(this.start, Instant.now());
        if (duration.toSeconds() > 1000) {
            return duration.toMinutes() + "m";
        }
        if (duration.toMillis() > 1000)
            return duration.toSeconds() + "s";

        if (duration.toMillis() < 1) {
            return duration.toNanos() / 1000 + "µs";
        }

        return duration.toMillis() + "ms";
    }

    public void start() {
        this.start = Instant.now();
    }
}
