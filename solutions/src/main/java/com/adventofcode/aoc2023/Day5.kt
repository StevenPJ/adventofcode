package com.adventofcode.aoc2023

import com.adventofcode.Solution

class Day5 : Solution() {

    override fun part1(input: String): Long {
        val (seedRanges, almanac) = parse(input, false)

        return lowestSeedLocation(seedRanges, almanac)
    }

    override fun part2(input: String): Long {
        val (seedRanges, almanac) = parse(input, true)

        return lowestSeedLocation(seedRanges, almanac)
    }

    private fun parse(input: String, asRanges: Boolean): Pair<List<LongRange>, Almanac> {
        val seeds = input.toNumbers().first()
        val almanac = input.splitBlocks().drop(1)
                .map { it.toNumbers().map {(src, dst, size) -> range(src, size) to range(dst, size) }}

        return if (asRanges) {
            val ranges = seeds.windowed(2, 2).map { (start, length) -> range(start, length) }
            Pair(ranges, almanac)
        } else {
            Pair(seeds.map { it..it }, almanac)
        }
    }

    private fun lowestSeedLocation(ranges: List<LongRange>, almanac: Almanac): Long {
        return ranges.minOf{ range ->
            var mapInputs = mutableListOf(range)
            for (map in almanac) {
                val mapped = mutableListOf<LongRange>()
                for ((dst, src) in map) {
                    val unmapped = mutableListOf<LongRange>()
                    while (mapInputs.isNotEmpty()) {
                        val (tooSmall, intersection, tooBig) = mapInputs.removeFirst().intersect(src)
                        intersection?.let { mapped += (it.first - src.first + dst.first)..(it.last - src.first + dst.first)}
                        tooSmall?.let { unmapped += it}
                        tooBig?.let { unmapped += it}
                    }
                    // process only unmapped entries for the next row
                    mapInputs = unmapped
                }
                // update the inputs for the next map
                mapInputs += mapped
            }
            // mapping is done, so we now have ranges of locations, and we can get the smallest
            mapInputs.minOf{ it.first }
        }
    }
}

private typealias Almanac = List<List<Pair<LongRange, LongRange>>>