package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day9 extends Solution {


    @Override
    def part1(String input) {
        def rope = getRope(2)
        executeMovesOn(rope, input)
        return rope.tail().positions.unique().size()
    }

    @Override
    def part2(String input) {
        def rope = getRope(10)
        executeMovesOn(rope, input)
        return rope.tail().positions.unique().size()
    }

    static executeMovesOn(Knot rope, String input) {
        input.tokenize().each {
            if (it.isNumber()) {
                Integer.parseInt(it).times {
                    rope.move()
                }
            } else {
                rope.setDirection(it)
            }
        }
    }

    static getRope(int nKnots) {
        def origin = new Vector(0, 0)
        List<Knot> rope = []
        nKnots.times {
            if (rope.isEmpty()) {
                rope.add(new Knot(origin))
            } else {
                rope.add(new Knot(origin, rope.last()))
            }
        }
        return rope.last()
    }
}

class Knot {
    Vector position
    Vector directionVector
    Knot next
    List<Vector> positions = []

    Knot(Vector origin) {
        this(origin, null)
    }

    Knot(Vector origin, Knot next) {
        this.position = origin
        this.positions.add(origin)
        this.next = next
    }

    Knot tail() {
        def tail = this
        while (tail.next != null) {
            tail = tail.next
        }
        return tail
    }

    void setDirection(String direction) {
        switch (direction) {
            case 'R':
                directionVector = new Vector(1, 0)
                break
            case 'L':
                directionVector = new Vector(-1, 0)
                break
            case 'U':
                directionVector = new Vector(0, 1)
                break
            case 'D':
                directionVector = new Vector(0, -1)
                break
            default:
                throw new RuntimeException("Unrecognized direction" + direction)
        }
    }

    void move() {
        position += directionVector
        positions.add(position)
        if (next != null && position.isNotAdjacentTo(next.position)) {
            next.directionVector = next.position.directionTowards(position)
            next.move()
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