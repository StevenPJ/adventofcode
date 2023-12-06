package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import kotlin.math.max
import kotlin.math.min

class Day5 : Solution() {

    override fun part1(input: String): Long {
        val maps = input.splitIgnoreEmpty("\n\n").toMutableList()
        val seeds = maps.removeAt(0).replace("seeds: ", "").split(" ").map { it.toLong() }

        return lowestSeedLocation(seeds, almanac(maps))
    }

    override fun part2(input: String): Long {
        val maps = input.splitIgnoreEmpty("\n\n").toMutableList()
        val seeds = maps.removeAt(0).replace("seeds: ", "").split(" ").map { it.toLong() }
        val ranges = (seeds.indices step 2).map { seeds[it] until seeds[it] + seeds[it + 1] }

        return lowestSeedLocationFromRanges(ranges, almanac(maps))
    }

    private fun almanac(input: List<String>): Almanac {
        return input.map { map ->
            map.nonEmptyLines().drop(1).map { row ->
                val (src, dst, size) = row.splitIgnoreEmpty(" ").map { it.toLong() }
                (src until src + size) to (dst until dst + size)
            }
        }
    }

    private fun lowestSeedLocation(seeds: List<Long>, almanac: Almanac): Long {
        return seeds.minOf{ seed ->
            var value = seed
            map@ for (map in almanac) {
                for (row in map) {
                    if (row.second.contains(value)) {
                        value = row.first.first + value - row.second.first
                        continue@map
                    }
                }
            }
            value
        }
    }

    private fun lowestSeedLocationFromRanges(ranges: List<LongRange>, almanac: Almanac): Long {
        return ranges.minOf{ range ->
            var mapInputs = mutableListOf(range)
            for (map in almanac) {
                val mapped = mutableListOf<LongRange>()
                for ((dst, src) in map) {
                    val unmapped = mutableListOf<LongRange>()
                    while (mapInputs.isNotEmpty()) {
                        val (tooSmall, intersection, tooBig) = mapInputs.removeFirst().intersect(src)
                        intersection?.let { mapped += (it.first - src.first + dst.first)..((it.last - src.first) + dst.first)}
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
            (mapInputs).minOf{ it.first }
        }
    }
}

private typealias Almanac = List<List<Pair<LongRange, LongRange>>>