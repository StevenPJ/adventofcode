package com.adventofcode.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class Solution implements Comparable<Solution> {
    private final int year;

    private final int day;

    public Solution(int year, int day) {
        this.year = year;
        this.day = day;
    }

    public Answer part1() {
        try {
            return new Answer(part1("input"));
        } catch (UnsupportedOperationException ex) {
            return Answer.UNSOLVED;
        }
    }

    public Answer part2() {
        try {
            return new Answer(part2("input"));
        } catch (UnsupportedOperationException ex) {
            return Answer.UNNEEDED;
        }
    }

    protected Object part1(String input) {
        throw new UnsupportedOperationException();
    }

    protected Object part2(String input) {
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
