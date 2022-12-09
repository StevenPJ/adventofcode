package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day9 extends Solution {

    @Override
    def part1(String input) {
        def rope = getRope(2)
        def moves = getMoves(input)
        return getTailPositionAfter(rope, moves).size()
    }

    @Override
    def part2(String input) {
        def rope = getRope(10)
        def moves = getMoves(input)
        return getTailPositionAfter(rope, moves).size()
    }

    static getTailPositionAfter(Knot rope, List<Vector> moves) {
        def tailPositions = []
        moves.each {
            rope.move(it)
            tailPositions.add(rope.tail().position)
        }
        return tailPositions.unique()
    }

    static List<Vector> getMoves(String input) {
        input.split("\n")
                .collect {
                    def move = it.split(' ')
                    move[0] * Integer.parseInt(move[1])
                }
                .flatten()
                .join("")
                .collect { directions.get(it) }
    }

    static def directions = [
            'R': new Vector(1, 0),
            'L': new Vector(-1, 0),
            'U': new Vector(0, 1),
            'D': new Vector(0, -1)
    ]

    static getRope(int nKnots) {
        (1..nKnots).inject(null) {
            previous, i -> new Knot(new Vector(0, 0), previous as Knot)
        }
    }
}

class Knot {
    Vector position
    Knot next

    Knot(Vector origin, Knot next) {
        this.position = origin
        this.next = next
    }

    Knot tail() {
        return next == null ? this : next.tail()
    }

    void move(Vector direction) {
        position += direction
        if (next != null && position.isNotAdjacentTo(next.position)) {
            next.move(next.position.directionTowards(position))
        }
    }
}

class Vector {
    int x, y

    Vector(int x, int y) {
        this.x = x
        this.y = y
    }

    @Override
    boolean equals(Object other) {
        return this.x == other.x && this.y == other.y
    }

    Vector multiply(int number) {
        return new Vector(x * number, y * number)
    }

    Vector plus(Vector other) {
        return new Vector(x + other.x, y + other.y)
    }

    Vector directionTowards(Vector other) {
        if (other == this)
            return new Vector(0, 0)
        if (x == other.x)
            return new Vector(0, other.y > y ? 1 : -1)
        if (y == other.y)
            return new Vector(other.x > x ? 1 : -1, 0)
        return new Vector(other.x > x ? 1 : -1, other.y > y ? 1 : -1)
    }

    boolean isNotAdjacentTo(Vector other) {
        return Math.abs(this.x - other.x) > 1 || Math.abs(this.y - other.y) > 1
    }
}