package com.adventofcode.aoc2022

import com.adventofcode.Solution
import com.adventofcode.util.splitIgnoreEmpty


class Day19 : Solution() {

    override fun part1(input: String): Int {
        val blueprints = parseBlueprints(input)
        return blueprints.sumOf { it.getMaxGeodesAfter(24) * it.id }
    }

    override fun part2(input: String): Int {
        val blueprints = parseBlueprints(input)
        return blueprints.take(3).map { it.getMaxGeodesAfter(32) }.reduce(Int::times)
    }

    private fun parseBlueprints(input: String): List<Blueprint> {
        return input.splitIgnoreEmpty("\n").map { line ->
            val numbers = Regex("[0-9]+").findAll(line).map(MatchResult::value).map { it.toInt() }.toList()
            Blueprint(
                id = numbers[0],
                costs = listOf(
                    listOf(numbers[1], 0, 0, 0),
                    listOf(numbers[2], 0, 0, 0),
                    listOf(numbers[3], numbers[4], 0, 0),
                    listOf(numbers[5], 0, numbers[6], 0),
                )
            )
        }
    }

    data class Blueprint(val id: Int, val costs: List<Resources>) {

        fun getMaxGeodesAfter(maxTime: Int): Int {
            val q = mutableListOf(listOf(0,0,0,0,1,0,0,0,0))
            val visited = mutableSetOf<State>()
            var maxGeodes = 0
            while (q.isNotEmpty()) {
                val state = q.removeFirst()
                if (state in visited || state.elapsed > maxTime || state.maxGeodesAfter(maxTime) < maxGeodes)
                    continue
                maxGeodes = maxGeodes.coerceAtLeast(state[3])
                q.addAll(0, state.next(this).map { it.prune(this, maxTime) }.distinct())
                visited += state
            }
            return maxGeodes
        }

        val maxRobots = enumValues<Resource>()
            .map { resource -> costs.maxOfOrNull { it[resource.index] } ?: 0 }
            .dropLast(1) + Int.MAX_VALUE

        fun costOf(type: Resource): Resources {
            return costs[type.index]
        }

        fun canAfford(type: Resource, resources: Resources): Boolean {
            return (resources - costs[type.index]).all { it >= 0 }
        }
    }
}

enum class Resource(val index: Int) { Ore(0), Clay(1), Obsidian(2), Geode(3) }
typealias State = List<Int>
typealias Robots = List<Int>
typealias Resources = List<Int>

fun Resources.accrueFrom(robots: Robots): Resources {
    return listOf(this[0] + robots[0], this[1] + robots[1], this[2] + robots[2], this[3] + robots[3])
}

operator fun Resources.minus(cost: Resources): Resources {
    return listOf(this[0] - cost[0], this[1] - cost[1], this[2] - cost[2], this[3] - cost[3])
}

fun Robots.addRobotOfType(type: Resource): Robots {
    return this.mapIndexed { k, v -> if (k == type.index) v + 1 else v }
}

operator fun Robots.compareTo(resources: Resources): Int {
    return resources.mapIndexed { k, v -> if (this[k] > v) 1 else 0 }.sum()
}

fun Int.triangular(): Int {
    return (this * this + 1) / 2
}
private val State.resources: Resources
    get() = take(4)

private val State.robots: Robots
    get() = subList(4, 8)

private val State.elapsed: Int
    get() = last()

private fun State.nextAccrueOnly(): State {
    return resources.accrueFrom(robots) + robots + (elapsed + 1)
}

private fun State.nextWithNewRobot(blueprint: Day19.Blueprint, resource: Resource): State? {
    if (blueprint.canAfford(resource, resources)) {
        val resources = resources.accrueFrom(robots) - blueprint.costOf(resource)
        val robots = robots.addRobotOfType(resource)
        return resources + robots + (elapsed + 1)
    }
    return null
}

fun State.next(blueprint: Day19.Blueprint): Set<State> {
    val newGeode = nextWithNewRobot(blueprint, Resource.Geode)
    val newObsidian = nextWithNewRobot(blueprint, Resource.Obsidian)
    val newClay = nextWithNewRobot(blueprint, Resource.Clay)
    val newOre = nextWithNewRobot(blueprint, Resource.Ore)
    if (newGeode != null)
        return setOf(newGeode)
    if (newObsidian != null && newClay != null)
        return setOf(newObsidian, newClay)
    if (newObsidian != null)
        return setOf(newObsidian)
    return setOfNotNull(newClay, newOre, nextAccrueOnly())
}

private fun State.maxGeodesAfter(time: Int): Int {
    val remaining = (time - elapsed)
    return remaining * robots[3] + remaining.triangular() + resources[3]
}

fun State.prune(blueprint: Day19.Blueprint, maxTime: Int): State {
    val remaining = (maxTime - elapsed)
    val prunedResources =  resources.mapIndexed { k, v -> if (k == 3) v else v.coerceAtMost((remaining * blueprint.maxRobots[k]) - (robots[k] * (remaining - 1))) }
    val prunedRobots =  robots.mapIndexed { k, v -> v.coerceAtMost(blueprint.maxRobots[k]) }
    return prunedResources + prunedRobots + elapsed
}
