package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import lombok.ToString

class Day15 extends Solution {

    @Override
    def part1(String input) {
        part1(input, 2000000)
    }

    @Override
    def part2(String input) {
        part2(input, 4000000)
    }

    def part1(String input, int y) {
        def sensors = parseSensorReport(input)
        def scanned = Line.combine(sensors*.scannedLine(y))
        return scanned.sum{it.size()}
    }

    def part2(String input, int maxY) {
        def sensors = parseSensorReport(input)
        def result = [].toSet()
        sensors.find { sensor ->
            def undetected = sensor.findPointInPerimeterThatDoesNotOverlapWith(sensors, maxY)
            if (undetected != null) {
                result << (BigInteger.valueOf(4000000) * undetected.x) + undetected.y
                return true
            }
        }
        return result.first()
    }

    static def parseSensorReport(String input) {
         input
                .replace("Sensor at x=", "")
                .replace("y=", "")
                .replace("closest beacon is at x=", "")
                .replace(":", ",")
                .replace(",", " ")
                .tokenize()
                .collate(4)
                .collect { new Sensor(
                        new Vector(it[0] as int, it[1] as int),
                        new Vector(it[2] as int, it[3] as int))
                }
    }
}

@EqualsAndHashCode
@ToString
@TupleConstructor(includeFields = true)
class Line {
    int start, end, y

    static List<Line> combine(List<Line> lines) {
        lines = lines.sort{it.start}.findAll{it.start <= it.end}.unique()
        def result = [lines.first()]
        for (int i = 1; i < lines.size(); i++) {
            def current = lines.get(i)
            def last = result.last()
            if (current.start <= last.end + 1) {
                result = result.dropRight(1) + new Line(last.start, Math.max(last.end, current.end), last.y)
            } else {
                result << current
            }
        }
        return result
    }

    int size() {
        end - start
    }

    List<Vector> perimeterNodes() {
        [new Vector(start - 1, y), new Vector(end - 1, y)]
    }
}

@EqualsAndHashCode
@ToString
class Sensor {
    Vector position
    Vector beacon
    int distance

    Sensor(Vector position, Vector beacon) {
        this.position = position
        this.beacon = beacon
        this.distance = position.manhattanDistanceTo(beacon)
    }

    Line scannedLine(int y) {
        int radius = distance - Math.abs(position.y - y)
        return new Line(position.x - radius, position.x + radius, y)
    }

    def contains(Vector other) {
        return position.manhattanDistanceTo(other) <= distance
    }

    Vector findPointInPerimeterThatDoesNotOverlapWith(List<Sensor> others, int max) {
        for (int y=position.y - distance; y<=position.y + distance; y++) {
            def uncovered = scannedLine(y).perimeterNodes().find{ point ->
                inBoundary(point, max) && others.find{it.contains(point)} == null
            }
            if (uncovered != null)
                return uncovered
        }
    }

    static boolean inBoundary(Vector vector, int max) {
        return vector.x >= 0 && vector.x <= max && vector.y >= 0 && vector.y <= max
    }
}