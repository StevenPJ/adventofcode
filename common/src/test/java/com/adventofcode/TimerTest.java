package com.adventofcode;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class TimerTest {

    @SneakyThrows
    @Test
    public void shouldCountElapsedTime() {
        var timer = new Timer();
        timer.start();
        Thread.sleep(50);
        var fiftyMillis = timer.elapsed();
        timer.start();
        Thread.sleep(10);
        var tenMillis = timer.elapsed();

        assertThat(fiftyMillis).isGreaterThan(tenMillis);
    }

}