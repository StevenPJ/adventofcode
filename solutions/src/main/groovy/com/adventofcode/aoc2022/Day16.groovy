package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

class Day16 extends Solution {

    @Override
    def part1(String input) {
        def graph = parseValves(input)
        def paths = graph.paths()
        def workingValves = graph.workingValves()
        findMostPressure('AA', 0, 30, paths, workingValves, graph, new HashSet<String>())
    }

    @Override
    def part2(String input) {
        def graph = parseValves(input)
        def paths = graph.paths()
        def workingValves = graph.workingValves()

        findMostPressureTwo('AA', 'AA', 0, 0, 26, paths, workingValves, graph, new HashSet<String>())
    }

    def findMostPressure(String currentValve, int timeElapsed, int maxTime, Map<String, Map<String,Integer>> paths, Set<String> workingValves, Graph graph, HashSet<String> openedValves) {
        def candidates = workingValves.findAll{!openedValves.contains(it)}
        candidates.collect{ valve ->
            def timeToValve = timeElapsed + paths[currentValve][valve] + 1
            if (timeToValve > maxTime)
                return 0
            def minutesOpened = maxTime - timeToValve
            def totalPressureFromValve = minutesOpened * graph.nodes[valve]
            return totalPressureFromValve + findMostPressure(valve, timeToValve, maxTime, paths, workingValves, graph, openedValves + valve)
        }.max() ?: 0
    }

    def findMostPressureTwo(String currentValve, String elephantCurrentValve, int timeElapsed, int elephantTime, int maxTime, Map<String, Map<String,Integer>> paths, Set<String> workingValves, Graph graph, HashSet<String> openedValves) {
        def candidates = workingValves.findAll{!openedValves.contains(it)}
        def elephantCandidates = workingValves.findAll{!openedValves.contains(it)}
        candidates.collect{ valve ->
            def timeToValve = timeElapsed + paths[currentValve][valve] + 1
            if (timeToValve > maxTime)
                return 0
            def minutesOpened = maxTime - timeToValve
            def totalPressureFromValve = minutesOpened * graph.nodes[valve]
            return totalPressureFromValve + elephantCandidates.collect {elephantValve ->
                if (elephantValve == valve)
                    return -1
                def elephantTimeToValve = elephantTime + paths[elephantCurrentValve][elephantValve] + 1
                if (elephantTimeToValve > maxTime)
                    return 0
                def elephantMinutesOpened = maxTime - elephantTimeToValve
                def totalPressureFromElephant = elephantMinutesOpened * graph.nodes[elephantValve]
                return totalPressureFromElephant + findMostPressureTwo(valve, elephantValve, timeToValve, elephantTimeToValve, maxTime, paths, workingValves, graph, openedValves + valve + elephantValve)

            }.max() ?: 0
        }.max() ?: 0
    }

    static def parseValves(String input) {
        def graph = new Graph()
        input.split(/\n/).each {
            def (_, valve, flowrate, neighbours) = (it =~ ~/Valve (\w+) has flow rate=(\d+); tunnel[s]? lead[s]? to valve[s]? (.*)/).findAll()[0]
            graph.nodes[valve] = Integer.parseInt(flowrate)
            graph.edges[valve] = neighbours.tokenize(',').collect{it.trim()}
        }
       return graph
    }
}

@EqualsAndHashCode
@TupleConstructor(includeFields = true)
class Graph {
    Map<String, Integer> nodes = [:]
    Map<String, List<String>> edges = [:]

    Map<String, Map<String, Integer>> paths() {
       nodes.keySet().collectEntries{ [(it): BFS(it)]}
    }

    def workingValves() {
        return nodes.findAll { it.value > 0}.keySet()
    }

    def BFS(String source) {
        def unsettled = [source]
        def settled = []
        def paths = [(source): 0].withDefault { key -> Integer.MAX_VALUE }
        while (!unsettled.isEmpty()) {
            def evaluation = unsettled.pop()
            edges[evaluation].each {
                if (!settled.contains(it)) {
                    def sourceDistance = paths.get(evaluation)
                    if (sourceDistance + 1 < paths.get(it)) {
                        paths.put(it, sourceDistance + 1)
                    }
                    unsettled.add(it)
                }
            }
            settled << evaluation
            unsettled = unsettled.unique().sort{paths.get(it)}
        }
        return paths
    }
}


