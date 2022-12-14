package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.transform.EqualsAndHashCode
import lombok.ToString


class Day9 extends Solution {

    @Override
    def part1(String input) {
        def rope = getRope(2)
        def moves = getMoves(input)
        return getTailPositionsAfter(rope, moves).unique().size()
    }

    @Override
    def part2(String input) {
        def rope = getRope(10)
        def moves = getMoves(input)
        return getTailPositionsAfter(rope, moves).unique().size()
    }

    static getTailPositionsAfter(Knot rope, List<Vector> moves) {
        moves.collect{
            rope.move(it)
            rope.tail().position
        }
    }

    static List<Vector> getMoves(String input) {
        input.tokenize()
                .collate(2)
                .collect{ it[0] * Integer.parseInt(it[1]) }
                .join()
                .collect { Vector.directions.get(it)}
    }

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

@EqualsAndHashCode
@ToString
public class Vector {
    public static def directions = [
            'R': new Vector(1, 0),
            'L': new Vector(-1, 0),
            'U': new Vector(0, 1),
            'D': new Vector(0, -1)
    ]

    int x, y

    Vector(int x, int y) {
        this.x = x
        this.y = y
    }

    Vector plus(Vector other) {
        return new Vector(x + other.x, y + other.y)
    }

    Vector minus(Vector other) {
        return new Vector(x - other.x, y - other.y)
    }

    Vector multiply(int constant) {
        return new Vector(x * constant, y * constant)
    }

    int magnitude() {
        return Math.sqrt(this.x*this.x + this.y*this.y)
    }

    Vector normalize() {
        def newX = this.x == 0 ? 0 : this.x.intdiv(Math.abs(this.x))
        def newY = this.y == 0 ? 0 : this.y.intdiv(Math.abs(this.y))
        return new Vector(newX, newY)
    }

    int manhattanDistanceTo(Vector other) {
        Math.abs(this.x - other.x) + Math.abs(this.y - other.y)
    }

    Set<Integer[]> pointsAlongLine(Vector other, int nPoints) {
        def directionVector = (other - this).normalize()
        (1..nPoints).collect{
            def result = this + (directionVector * (it-1) )
            return [result.x, result.y].toArray()
        }
    }
}