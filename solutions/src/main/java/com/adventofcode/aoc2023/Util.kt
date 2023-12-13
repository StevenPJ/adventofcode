package com.adventofcode.aoc2023

import com.adventofcode.util.nonEmptyLines
import com.adventofcode.util.splitIgnoreEmpty
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class Dimensions(val width: Int, val height: Int) {

    constructor(input: List<String>)  : this(
            input.first().length,
            input.size)

    fun contains(p: Point): Boolean {
        return p.x >= 0 && p.x < this.width && p.y < this.height && p.y >= 0
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

    fun name(): String {
        return String.format("(%d,%d)", x, y)
    }

    fun left(): Point {
        return Point(this.x - 1, this.y)
    }

    fun move(vector: Point): Point {
        return Point(x + vector.x, y + vector.y)
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
    return this.nonEmptyLines().map { "(-?\\d+)".toRegex().findAll(it).map { it.value.toLong() }.toList() }.filter{ it.isNotEmpty() }
}

fun Long.toDigits(): List<Long> = toString().map { it.toString().toLong() }

fun <T> List<T>.firstAndLast(): List<T> = this.slice(setOf(0, this.size - 1))
fun List<Long>.toDigits(): List<Long> = this.flatMap { it.toDigits() }
fun List<Long>.toNumber() : Long = "${this.first()}${this.last()}".toLong()
fun String.splitBlocks(): List<String> = this.splitIgnoreEmpty("\n\n")

fun range(start: Long, length: Long): LongRange = start until start + length

data class Node(val name: String, val neighbours: List<String>) {
    fun left(nodes: List<Node>): Node = nodes.find { it.name == this.neighbours.first() }!!
    fun right(nodes: List<Node>): Node = nodes.find { it.name == this.neighbours.last() }!!
    fun neighbours(nodes: List<Node>): List<Node> = neighbours.map { n -> nodes.find { it.name == n }!! }
}

fun path(next: (current: Node) -> Node, source: Node, sinkTest: (Node) -> Boolean): List<Node> {
    val nodes = mutableListOf(source)
    while (!sinkTest(nodes.last())) {
        nodes += next(nodes.last())
    }
    return nodes
}

fun List<Node>.shortestPath(source: String): Map<String, Int> {
    var unsettled = ArrayDeque(listOf(source))
    val settled = mutableListOf<String>()
    val paths = hashMapOf(source to 0).withDefault { Int.MAX_VALUE }
    while (!unsettled.isEmpty()) {
        val evaluation = unsettled.removeFirst()
        this.find { it.name == evaluation }!!.neighbours(this).forEach {
            if (!settled.contains(it.name)) {
                val sourceDistance: Int = paths[evaluation] ?: Int.MAX_VALUE
                if (sourceDistance + 1 < (paths[it.name] ?: Int.MAX_VALUE)) {
                    paths[it.name] = sourceDistance + 1
                }
                unsettled.add(it.name)
            }
        }
        settled.add(evaluation)
        unsettled = ArrayDeque(unsettled.distinct().sortedBy { paths[it] })
    }
    return paths
}

fun List<Node>.dfs(source: String, discovered: MutableList<Node> = mutableListOf()) : List<Node> {
    val node = this.find { it.name == source }!!
    discovered.add(node)
    node.neighbours(this).forEach {
        if (!discovered.contains(it))
            return this.dfs(it.name, discovered)
    }
    return discovered
}

fun List<Node>.bfs(source: String): List<Node> {
    val node = this.find { it.name == source }!!
    val queue = mutableListOf(node)
    val visited = mutableListOf(node)

    while(queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        current.neighbours(this).forEach {
            if (!visited.contains(it)) {
                visited.add(it)
                queue.add(it)
            }
        }
    }
    return visited
}

fun Node.point(): Point {
    val (x, y) = this.name.match("\\((-?\\d+),(-?\\d+)\\)".toRegex())!!
    return Point(x.toInt(), y.toInt())
}

fun Node.x(): Int {
    return this.point().x
}

fun Node.y(): Int {
    return this.point().y
}

fun List<Node>.shoelaceArea(): Double {
    val n = this.size
    var area = 0.0
    (0 until n - 1).forEach{
        area += this[it].x() * this[it + 1].y() - this[it + 1].x() * this[it].y()
    }
    return abs(area + this[n - 1].x() * this[0].y() - this[0].x() * this[n - 1].y()) / 2.0
}

fun Point.distance(other: Point): Int {
    return abs(other.x - this.x) + abs(other.y - this.y)
}

fun <T>List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

fun <T> List<T>.rotate(n: Int): List<T> {
    val rotated = this.toMutableList()
    Collections.rotate(rotated, n)
    return rotated
}
