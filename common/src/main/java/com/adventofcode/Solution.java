package com.adventofcode;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;

@EqualsAndHashCode
public abstract class Solution implements Comparable<Solution> {
    @SneakyThrows
    public Object part1() {
        return part1(getInput());
    }

    @SneakyThrows
    public Object part2() {
        return part2(getInput());
    }

    @SneakyThrows
    private String getInput() {
        var inputDir = System.getProperty("INPUT_DIR");
        var inputFile = new File(inputDir + String.format("/%d/day%d.txt", year(), day()));
        return FileUtils.readFileToString(inputFile, "UTF-8");
    }

    protected Object part1(String input) {
        throw new UnsupportedOperationException();
    }
    protected Object part2(String input) {
        throw new UnsupportedOperationException();
    }

    public int year() {
        return Integer.valueOf(this.getClass().getPackageName().replace("com.adventofcode.aoc", ""));
    }

    public int day() {
        return Integer.valueOf(this.getClass().getSimpleName().replace("Day", ""));
    }

    public boolean matches(int year, int day) {
        return this.year() == year && this.day() == day;
    }

    public boolean matches(int year) {
        return this.year() == year;
    }

    @Override
    public int compareTo(Solution other) {
        if (this.year() != other.year()) {
            return Integer.compare(this.year(), other.year());
        }
        return Integer.compare(this.day(), other.day());
    }
}
