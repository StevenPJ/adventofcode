package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.repeatIndefinitely


class Day17 : Solution() {

    override fun part1(input: String): Int {
        val chamber = Chamber(input)
        var stoppedRocks = 0
        var rock = chamber.loadNext()
        while (stoppedRocks < 2022) {
            while (true) {
                val (next, atRest) = chamber.move(rock)
                if (atRest)
                    break
                rock = next
            }
            chamber.save(rock)
            rock = chamber.loadNext()
            stoppedRocks++
        }
        return chamber.height()
    }

    data class Rock(var maxRight: Int, val shape: List<Row>, val maxLeft: Int = 2) {

        fun left(): Rock {
            return if (maxLeft == 0) this
            else Rock(maxRight + 1, shape.map { it shl 1 }, maxLeft - 1)
        }

        fun right(): Rock {
            return if (maxRight == 0) this
            else Rock(maxRight - 1, shape.map { it shr 1 }, maxLeft + 1)
        }

        fun down(): Rock {
            return Rock(maxRight, listOf(0b0000000) + shape, maxLeft)
        }

        override fun toString(): String {
            return shape.joinToString("\n") { it.toRow() }
        }
    }

    data class Chamber(val jetStream: String, val state: ArrayList<Row> = arrayListOf()) {

        private val jets = jetStream.trim()
            .replace("", "v")
            .toCharArray()
            .toList()
            .drop(1)
            .repeatIndefinitely()
            .iterator()

        private val rocks = listOf(
            Rock(1, listOf(0b0011110)),
            Rock(2, listOf(0b0001000, 0b0011100, 0b0001000)),
            Rock(2, listOf(0b0000100, 0b0000100, 0b0011100)),
            Rock(4, List(4) { 0b0010000 }),
            Rock(3, List(2) { 0b0011000 }),
        ).repeatIndefinitely().iterator()

        fun loadNext(): Rock {
            val next = rocks.next()
            repeat(3 + next.shape.size) { this.state.add(0, 0b0000000) }
            return next
        }

        fun save(rock: Rock) {
            rock.shape.forEachIndexed { index, row -> state[index] = state[index] or row }
            state.removeIf { it == 0 }
        }

        fun move(rock: Rock): Pair<Rock, Boolean> {
            val jet = jets.next()
            val next = when (jet) {
                '<' -> rock.left()
                '>' -> rock.right()
                'v' -> rock.down()
                else -> rock
            }
            if (this.collidesWith(next))
                return if (jet == 'v') rock to true
                else rock to false
            return next to false
        }

        private fun collidesWith(rock: Rock): Boolean {
            rock.shape.forEachIndexed { index, row ->
                if ((index) >= state.size || row and state[index] > 0)
                    return true
            }
            return false
        }

        fun height(): Int {
            val grid = state.toMutableList()
            grid.removeIf { it == 0 }
            return grid.size
        }

        override fun toString(): String {
            return state.joinToString("\n") { "|" + it.toRow() + "|" } + "\n+-------+"
        }
    }
}

typealias Row = Int

fun Row.toRow(): String =
    this.toString(2)
        .padStart(7, '0')
        .replace("0", ".")
        .replace("1", "#")