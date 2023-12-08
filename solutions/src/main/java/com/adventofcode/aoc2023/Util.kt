package com.adventofcode.aoc2023

import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class Dimensions(private val width: Int, private val height: Int) {

    constructor(input: List<String>)  : this(
            input.first().length,
            input.size)

    fun contains(p: Point): Boolean {
        return p.x >= 0 && p.x <= this.width && p.y <= this.height && p.y >= 0
    }
}

data class Point(val x: Int, val y: Int) {

    fun neighboursWithin(dimensions: Dimensions): List<Point> {
        return listOf(
                Point(this.x - 1, this.y),
                Point(this.x, this.y + 1),
                Point(this.x, this.y - 1),
                Point(this.x - 1, this.y + 1),
                Point(this.x - 1, this.y - 1),
                Point(this.x + 1, this.y - 1),
                Point(this.x + 1, this.y),
                Point(this.x + 1, this.y + 1),
        ).filter { dimensions.contains(it) }
    }

    fun left(): Point {
        return Point(this.x - 1, this.y)
    }
}

fun List<String>.charAt(p: Point): Char {
    return this[p.y][p.x]
}

fun solveQuadratic(aIn: Number, bIn: Number, cIn: Number): Pair<Double, Double> {
    val a = aIn.toDouble()
    val b = bIn.toDouble()
    val c = cIn.toDouble()
    val root1: Double
    val root2: Double

    val determinant = b * b - 4.0 * a * c

    // condition for real and different roots
    if (determinant > 0) {
        root1 = (-b + sqrt(determinant)) / (2 * a)
        root2 = (-b - sqrt(determinant)) / (2 * a)
        return Pair(root1, root2)
    }
    // Condition for real and equal roots
    else if (determinant == 0.0) {
        root2 = -b / (2 * a)
        return Pair(root2, root2)
    }
    // If roots are not real
    else {
        val realPart = -b / (2 * a)
        val imaginaryPart = sqrt(-determinant) / (2 * a)

        return Pair(realPart + imaginaryPart, realPart - imaginaryPart)
    }
}

fun LongRange.increases() : Boolean {
    return this.last > this.first
}

fun LongRange.intersect(other: LongRange) : Triple<LongRange?, LongRange?, LongRange?> {
    // handle ranges of size 1
    if (this.first == this.last) {
        return Triple(
                if (this.first < other.first) this else null,
                if (other.contains(this.first)) this else null,
                if (this.first > other.last) this else null,
        )
    }
    val intersection = max(this.first, other.first) .. min(this.last, other.last)
    val tooSmall = this.first .. min(this.last, other.first)
    val tooBig = max(this.first, other.last) .. this.last
    return Triple(
            if (tooSmall.increases()) tooSmall else null,
            if (intersection.increases()) intersection else null,
            if (tooBig.increases()) tooBig else null,
    )
}

fun String.match(expression: Regex): MatchResult.Destructured? {
    val matchResult = expression.find(this) ?: return null
    return matchResult.destructured
}

fun MatchResult.Destructured?.toInt() : Int {
    return this?.component1()?.toInt() ?: 0
}

fun String.toNumbers(): List<List<Long>> {
    return this.nonEmptyLines().map { line -> line.split("\\D+".toRegex()).filter { it.isNotEmpty() }.map { it.trim().toLong() }}.filter { it.isNotEmpty() }
}

fun Long.toDigits(): List<Long> = toString().map { it.toString().toLong() }

fun <T> List<T>.firstAndLast(): List<T> = this.slice(setOf(0, this.size - 1))
fun List<Long>.toDigits(): List<Long> = this.flatMap { it.toDigits() }
fun List<Long>.toNumber() : Long = "${this.first()}${this.last()}".toLong()
fun String.splitBlocks(): List<String> = this.splitIgnoreEmpty("\n\n")

fun range(start: Long, length: Long): LongRange = start until start + length

data class Node(val name: String, private val neighbours: Pair<String, String>) {
    fun left(nodes: List<Node>): Node = nodes.find { it.name == this.neighbours.first }!!
    fun right(nodes: List<Node>): Node = nodes.find { it.name == this.neighbours.second }!!
}