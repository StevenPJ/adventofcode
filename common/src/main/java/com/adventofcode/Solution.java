package com.adventofcode;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;

@EqualsAndHashCode
public abstract class Solution implements Comparable<Solution> {
    private final int year;

    private final int day;

    public Solution(int year, int day) {
        this.year = year;
        this.day = day;
    }

    @SneakyThrows
    public Object part1() {
        var inputDir = System.getProperty("INPUT_DIR");
        var inputFile = new File(inputDir + String.format("/%d/day%d.txt", year, day));
        var input = FileUtils.readFileToString(inputFile, "UTF-8");
        return part1(input);
    }

    protected Object part1(String input) {
        throw new UnsupportedOperationException();
    }

    public int year() {
        return this.year;
    }

    public int day() {
        return this.day;
    }

    public boolean matches(int year, int day) {
        return this.year == year && this.day == day;
    }

    @Override
    public int compareTo(Solution other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        }
        return Integer.compare(this.day, other.day);
    }
}
