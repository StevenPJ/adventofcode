package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.repeatIndefinitely
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import java.lang.RuntimeException
import java.math.BigInteger


class Day17 : Solution() {

    override fun part1(input: String): BigInteger {
        return heightAfter(BigInteger.valueOf(2022), input)
    }

    override fun part2(input: String): BigInteger {
        return heightAfter(BigInteger.valueOf(1000000000000), input)
    }

    private fun heightAfter(n: BigInteger, input: String): BigInteger {
        val hashes = hashMapOf<Int, Int>()
        val chamber = Chamber(input)
        var stoppedRocks = 0
        var rock = chamber.loadNext()
        while (stoppedRocks.toBigInteger() < n) {
            while (true) {
                val (next, atRest) = chamber.move(rock)
                if (atRest) break
                rock = next
            }
            chamber.save(rock)
            if (hashes[chamber.hashCode()] != null) {
                return calculateHeightUsingCycle(
                    m = n,
                    s = hashes[chamber.hashCode()]!!.toBigInteger(),
                    c = stoppedRocks.toBigInteger(),
                    input = input
                )
            } else hashes[chamber.hashCode()] = stoppedRocks
            rock = chamber.loadNext()
            stoppedRocks++
        }
        return chamber.height().toBigInteger()
    }

    private fun calculateHeightUsingCycle(m: BigInteger, s: BigInteger, c: BigInteger, input: String): BigInteger {
        val l = c - s
        val hCycle = heightAfter(c, input) - heightAfter(s, input)
        val nCycle = (m - s) / l
        val r = (m - s) % l
        val hr = heightAfter(r + s, input)
        return (hCycle * nCycle) + hr
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

        override fun hashCode(): Int {
            return HashCodeBuilder()
                .append(shape)
                .hashCode()
        }
    }

    data class Chamber(val input: String, val state: ArrayList<Row> = arrayListOf()) {
        private val jetStream = input.replace("\n", "").trim()
        private val jets = jetStream.trim()
            .replace("\n", "")
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

        private var currentRock: Rock? = null
        private var currentJetIndex: Int = 0

        fun loadNext(): Rock {
            val next = rocks.next()
            currentRock = next
            repeat(3 + next.shape.size) { this.state.add(0, 0b0000000) }
            return next
        }

        private fun nextJet(): Char {
            val next = jets.next()
            this.currentJetIndex++
            return next
        }

        fun save(rock: Rock) {
            rock.shape.forEachIndexed { index, row -> state[index] = state[index] or row }
            state.removeIf { it == 0 }
        }

        fun move(rock: Rock): Pair<Rock, Boolean> {
            val jet = nextJet()
            val next = when (jet) {
                '<' -> rock.left()
                '>' -> rock.right()
                'v' -> rock.down()
                else -> throw RuntimeException("Unrecognized input character: $jet")
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

        private fun maxHeightOfCols(): Array<Int> {
            val grid = state.toMutableList()
            grid.removeIf { it == 0 }
            val cols = Array(7) {this.height()}
            val masks = mutableListOf(0b1000000, 0b0100000, 0b0010000, 0b0001000, 0b0000100, 0b0000010, 0b0000001)
            grid.forEach{row ->
                if (masks.isEmpty())
                    return cols
                val haveValues = masks.filter { row and it > 0 }
                haveValues.forEach {mask ->
                    val index = masks.indexOf(mask)
                    cols[index] = grid.indexOf(row)
                }
                masks.removeAll(haveValues)
            }
            return cols
        }

        override fun hashCode(): Int {
            return HashCodeBuilder(17, 31)
                .append(currentJetIndex % (jetStream.length * 2))
                .append(currentRock)
                .append(maxHeightOfCols())
                .toHashCode()
        }

        override fun equals(other: Any?): Boolean {
            return when (other) {
                !is Chamber -> false
                this -> true
                else -> EqualsBuilder()
                    .append(currentJetIndex % jetStream.length , other.currentJetIndex % other.jetStream.length)
                    .append(currentRock, other.currentRock)
                    .append(maxHeightOfCols(), other.maxHeightOfCols())
                    .isEquals
            }
        }
    }
}

typealias Row = Int

fun Row.toRow(): String =
    this.toString(2)
        .padStart(7, '0')
        .replace("0", ".")
        .replace("1", "#")
