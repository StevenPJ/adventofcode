package com.adventofcode.common;

record Answer(Object answer) {

    public static final Answer UNNEEDED = new Answer("unneeded");
    public static final Answer UNSOLVED = new Answer("unsolved");

    @Override
    public String toString() {
        return answer.toString();
    }
}
