package com.adventofcode.aoc2023

import com.adventofcode.Solution

class Day9 : Solution() {


    override fun part1(input: String): Long {
        return input.toNumbers().sumOf { extrapolateNext(it.asSequence()) }
    }

    override fun part2(input: String): Long {
        return input.toNumbers().sumOf { extrapolateNext(it.reversed().asSequence()) }
    }

    private fun extrapolateNext(sequence: Sequence<Long>): Long {
        // create a new sequence of the deltas i.e. 0, 3 -> 3 and 1, 2, 3 -> 1, 1
        val deltas: (Sequence<Long>) -> Sequence<Long> = { it.zipWithNext { a, b -> b - a } }
        // generate all sequences of differences, retaining the input sequence
        val deltaSequences = generateSequence{}.runningFold(sequence) { lastSequence, _ -> deltas(lastSequence) }
        // evaluate the sequences, stopping when differences are 0, and summing the last element of each to find next
        return deltaSequences.takeWhile { seq -> seq.any { it != 0L} }.sumOf { it.last() }
    }
}