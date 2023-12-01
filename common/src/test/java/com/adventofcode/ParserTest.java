package com.adventofcode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ParserTest {

     Parser parser = new Parser();

    @Test
    void shouldReturnAllWhenNoArgsPassed() {
        var actual = parser.parse(new String[]{});

        assertThat(actual).isEqualTo(new Selector.All());
    }

    @Test
    void shouldReturnAll() {
        var actual = parser.parse(new String[]{"all"});

        assertThat(actual).isEqualTo(new Selector.All());
    }

    @Test
    void shouldReturnLast() {
        var actual = parser.parse(new String[]{"last"});

        assertThat(actual).isEqualTo(new Selector.Last());
    }

    @Test
    void shouldReturnOne() {
        var actual = parser.parse(new String[]{"2022", "1"});

        assertThat(actual).isEqualTo(new Selector.One(2022, 1));
    }

    @Test
    void shouldReturnOneWithPaddedDay() {
        var actual = parser.parse(new String[]{"2022", "02"});

        assertThat(actual).isEqualTo(new Selector.One(2022, 2));
    }

    @Test
    void shouldThrowWhenTooManyArgs() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> parser.parse(new String[]{"2022", "02", "extra"}));
    }

    @Test
    void shouldReturnYear() {
        var actual = parser.parse(new String[]{"2023"});

        assertThat(actual).isEqualTo(new Selector.Year(2023));
    }
}