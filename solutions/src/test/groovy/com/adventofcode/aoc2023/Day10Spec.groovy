package com.adventofcode.aoc2023

import spock.lang.Specification

class Day10Spec extends Specification {

    def example = '''\
    .....
    .S-7.
    .|.|.
    .L-J.
    .....
    '''.stripIndent()

    def example2 = '''\
    7-F7-
    .FJ|7
    SJLL7
    |F--J
    LJ.LJ
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day10().part1(example) == 4
        new Day10().part1(example2) == 8
    }

    def example3 = '''\
    ...........
    .S-------7.
    .|F-----7|.
    .||.....||.
    .||.....||.
    .|L-7.F-J|.
    .|..|.|..|.
    .L--J.L--J.
    ...........
    '''.stripIndent()

    def example4 = '''\
    .F----7F7F7F7F-7....
    .|F--7||||||||FJ....
    .||.FJ||||||||L7....
    FJL7L7LJLJ||LJ.L-7..
    L--J.L7...LJS7F-7L7.
    ....F-J..F7FJ|L7L7L7
    ....L7.F7||L7|.L7L7|
    .....|FJLJ|FJ|F7|.LJ
    ....FJL-7.||.||||...
    ....L---J.LJ.LJLJ...
    '''.stripIndent()

    def example5 = '''\
    FF7FSF7F7F7F7F7F---7
    L|LJ||||||||||||F--J
    FL-7LJLJ||||||LJL-77
    F--JF--7||LJLJ7F7FJ-
    L---JF-JLJ.||-FJLJJ7
    |F|F-JF---7F7-L7L|7|
    |FFJF7L7F-JF7|JL---7
    7-L-JL7||F7|L7F-7F7|
    L.L7LFJ|||||FJL7||LJ
    L7JLJL-JLJLJL--JLJ.L
    '''.stripIndent()

    def "part 2 example"() {

        expect:
        new Day10().part2(example3) == 4
        new Day10().part2(example4) == 8
        new Day10().part2(example5) == 10
    }
}
