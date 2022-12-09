package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day9 extends Solution {

    @Override
    def part1(String input) {
        def rope = getRope(2)
        def moves = getMoves(input)
        return getTailPositionsAfter(rope, moves).size()
    }

    @Override
    def part2(String input) {
        def rope = getRope(10)
        def moves = getMoves(input)
        return getTailPositionsAfter(rope, moves).size()
    }

    static getTailPositionsAfter(Knot rope, List<Vector> moves) {
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
        if (next != null) {
            def difference = position - next.position
            if (difference.magnitude() > 1)
                next.move(difference.normalize())
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

    Vector plus(Vector other) {
        return new Vector(x + other.x, y + other.y)
    }

    Vector minus(Vector other) {
        return new Vector(x - other.x, y - other.y)
    }

    int magnitude() {
        return Math.sqrt(this.x*this.x + this.y*this.y)
    }

    Vector normalize() {
        def newX = this.x == 0 ? 0 : this.x.intdiv(Math.abs(this.x))
        def newY = this.y == 0 ? 0 : this.y.intdiv(Math.abs(this.y))
        return new Vector(newX, newY)
    }
}