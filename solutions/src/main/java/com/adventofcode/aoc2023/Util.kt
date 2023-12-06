package com.adventofcode.aoc2023

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

fun completeTheSquare(aIn: Number, bIn: Number, cIn: Number): Pair<Double, Double> {
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