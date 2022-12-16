package com.adventofcode;

import java.time.Duration;
import java.time.Instant;

class Timer {

    Instant start;

    public String elapsed() {
        var duration =  Duration.between(this.start, Instant.now());
        if (duration.toMillis() > 1000)
            return duration.toSeconds() + "s";
        if (duration.toSeconds() > 1000)
            return duration.toMillis() + "m";
        return duration.toMillis() + "ms";
    }

    public void start() {
        this.start = Instant.now();
    }
}
