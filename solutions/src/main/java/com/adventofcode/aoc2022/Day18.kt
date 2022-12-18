package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.splitIgnoreEmpty


class Day18 : Solution() {

    override fun part1(input: String): Int {
        val cubes = parseCube(input)
        return cubes.sumOf { it.neighbours().count { it !in cubes } }
    }

    override fun part2(input: String): Int {
        val cubes = parseCube(input)
        val exteriorCubes = exterior(cubes)
        return cubes.sumOf { it.neighbours().count { it in exteriorCubes } }
    }

    private fun exterior(cubes: Set<Cube>): Set<Cube> {
        val range = Range(
            cubes.minOf { it.x } - 1..cubes.maxOf { it.x } + 1,
            cubes.minOf { it.y } - 1..cubes.maxOf { it.y } + 1,
            cubes.minOf { it.z } - 1..cubes.maxOf { it.z } + 1
        )
        val q = mutableListOf(range.first())
        val exteriorCubes = mutableSetOf<Cube>()
        val visited = mutableSetOf<Cube>()
        while (q.isNotEmpty()) {
            val cube = q.removeFirst()
            if (cube !in visited) {
                cube.neighbours()
                    .filter { range.contains(it) }
                    .forEach{ if (it in cubes) exteriorCubes += cube else q += it }
                visited += cube
            }
        }
        return exteriorCubes
    }

    private fun parseCube(input: String): Set<Cube> {
        return input.splitIgnoreEmpty("\n").map { line ->
            val (x, y, z) = """(\d+),(\d+),(\d+)""".toRegex().find(line)!!.destructured
            Cube(x.toInt(), y.toInt(), z.toInt())
        }.toSet()
    }

    data class Range(val x: IntRange, val y: IntRange, val z: IntRange) {

        fun contains(cube: Cube): Boolean {
            return cube.x in x && cube.y in y && cube.z in z
        }

        fun first(): Cube {
            return Cube(x.first, y.first, z.first)
        }
    }

    data class Cube(val x: Int, val y: Int, val z: Int) {

        fun neighbours(): Set<Cube> {
            return setOf(
                Cube(x + 1, y, z), Cube(x - 1, y, z),
                Cube(x, y + 1, z), Cube(x, y - 1, z),
                Cube(x, y, z + 1), Cube(x, y, z - 1)
            )
        }
    }
}