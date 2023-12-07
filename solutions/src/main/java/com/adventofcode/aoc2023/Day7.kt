package com.adventofcode.aoc2023

import com.adventofcode.Solution
import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty

class Day7 : Solution() {


    override fun part1(input: String): Int {
        return input.nonEmptyLines()
                .map { hand(it) }
                .sortedWith{ first, second -> camelCompare(first, second, cards) }
                .mapIndexed{ idx, hand -> hand.bet * (idx + 1) }
                .sum()
    }

    override fun part2(input: String): Int {
        return input.nonEmptyLines()
                .map { highestPossible(hand(it)) }
                .sortedWith{ first, second -> camelCompare(first, second, cards.filter { it != 'J' } + 'J') }
                .mapIndexed{ idx, hand -> hand.bet * (idx + 1) }
                .sum()
    }

    private fun highestPossible(hand: Hand) : Hand {
        val mostCommonOtherThanJ =  hand.type.firstOrNull{ it.first != 'J' }?.first?.toString() ?: "2"
        val highestHand = hand(hand.hand.replace("J", mostCommonOtherThanJ) + " " + hand.bet)
        return Hand(highestHand.type, hand.bet, hand.hand)
    }

    private fun hand(input: String) : Hand {
        val (hand, bet) = input.splitIgnoreEmpty(" ")
        val parsed = cards
                .filter { hand.contains(it) }
                .map { card -> card to hand.count { it == card } }
                .sortedByDescending { it.second }
        return Hand(parsed, bet.toInt(), hand)
    }

    private fun camelCompare(hand: Hand, other: Hand, cardRanks: List<Char>): Int {
        val handType = 2 * hand.type[0].second + hand.type.getOrElse(1) { _ -> Pair('2', 1) }.second
        val otherType = 2 * other.type[0].second + other.type.getOrElse(1) { _ -> Pair('2', 1) }.second

        if (handType == otherType)
            for (card in 0 until 5) {
                if (hand.hand[card] == other.hand[card]) {
                    continue
                }
                return cardRanks.indexOf(other.hand[card]) - cardRanks.indexOf(hand.hand[card])
            }
        return handType - otherType
    }

    private val cards = listOf('A', 'K', 'Q', 'J', 'T') + (9 downTo 2).map { it.toString()[0] }
    private data class Hand(val type: List<Pair<Char, Int>>, val bet: Int, val hand: String)
}
