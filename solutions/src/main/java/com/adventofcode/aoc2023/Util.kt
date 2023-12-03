package com.adventofcode.aoc2023

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