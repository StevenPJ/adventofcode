package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import java.util.function.Supplier

class Day12 : Solution() {


    override fun part1(input: String): Long {
        val damagedRecords = parse(input)
        return damagedRecords.sumOf{ nPossibleArrangements(record = it) }
    }

    override fun part2(input: String): Long {
        val damagedRecords = parse(input)
        val unfold = {record: DamagedRecord ->
            val springs = (record.springs + "?").repeat(5).dropLast(1)
            val groups = List(5){ record.groups}.flatten()
            DamagedRecord(springs, groups)
        }
        return damagedRecords
                .map { unfold(it) }
                .sumOf{ nPossibleArrangements(record = it) }
    }

    private fun nPossibleArrangements(arrangement: String = "", group: Int = 0, record: DamagedRecord, memo: Memo = Memo()): Long {
        return memo.computeIfAbsent(arrangement, group) {
            if (group == record.nGroups) {
                val possibleArrangement = arrangement + ".".repeat(record.nSprings - arrangement.length)
                if (record.supports(possibleArrangement)) 1 else 0
            } else {
                val maxArrangementSize = record.nSprings - record.groupSize(group)
                (0 until maxArrangementSize).sumOf {
                    val possibleArrangement = createArrangement(arrangement, group, it, record.groupSize(group))
                    if (record.supports(possibleArrangement))
                        nPossibleArrangements(possibleArrangement, group + 1, record, memo)
                    else 0
                }
            }
        }
    }

    private fun parse(input: String): List<DamagedRecord> {
        return input.nonEmptyLines().map {
            val records = it.split(" ")
            DamagedRecord(records[0], records[1].toNumbers().first())
        }
    }

    private data class DamagedRecord(val springs: String, val groups: List<Long>) {
        fun supports(arrangement: String): Boolean {
            if (arrangement.length > nSprings) return false
            return arrangement.mapIndexed{ idx, char -> char to idx}.all { (char, idx) -> springs[idx] == char || springs[idx] == '?' }
        }

        val nSprings = springs.length
        val nGroups = groups.size
        val groupSize = { group: Int -> groups[group].toInt()}
    }

    private fun createArrangement(arrangement: String, group: Int, operational: Int, broken: Int): String {
        return "$arrangement${if (group > 0) "." else ""}${".".repeat(operational)}${"#".repeat(broken)}"
    }

    private data class Memo(private val cache: MutableMap<Pair<Int, Int>, Long> = HashMap()) {
        fun computeIfAbsent(arrangement: String, group: Int, fn: Supplier<Long>): Long {
            if (cache.contains(Pair(arrangement.length, group)))
                return cache[Pair(arrangement.length, group)]!!
            val result = fn.get()
            cache[Pair(arrangement.length, group)] = result
            return result
        }
    }
}