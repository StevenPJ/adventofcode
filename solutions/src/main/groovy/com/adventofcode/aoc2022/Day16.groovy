package com.adventofcode.aoc2022

import com.adventofcode.Solution
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

class Day16 extends Solution {

    @Override
    def part1(String input) {
        def graph = parseValves(input)
        findMaxPressure(State.start(graph), 30)
    }

    @Override
    def part2(String input) {
        def graph = parseValves(input)
        findMaxPressureWithHelp(State.start(graph), State.start(graph), 26)
    }

    def findMaxPressure(State state, int maxTime) {
        state.workingValves.collect{ valve ->
            def timeToValve = state.timeToMoveToValve(valve)
            if (timeToValve > maxTime || state.hasOpened(valve))
                return 0
            def minutesOpened = maxTime - timeToValve
            def totalPressureFromValve = minutesOpened * state.flowRate(valve)
            return totalPressureFromValve + findMaxPressure(state.next(valve, timeToValve), maxTime)
        }.max() ?: 0
    }

    def findMaxPressureWithHelp(State player, State elephant, int maxTime) {
        player.workingValves.collect{ valve ->
            def timeToValve = player.timeToMoveToValve(valve)
            if (timeToValve > maxTime || player.hasOpened(valve))
                return 0
            def minutesOpened = maxTime - timeToValve
            def totalPressureFromValve = minutesOpened * player.flowRate(valve)
            return totalPressureFromValve + elephant.workingValves.collect { elephantValve ->
                def elephantTimeToValve = elephant.timeToMoveToValve(elephantValve)
                if (elephantTimeToValve > maxTime || elephant.hasOpened(elephantValve) ||  elephantValve == valve)
                    return 0
                def elephantMinutesOpened = maxTime - elephantTimeToValve
                def totalPressureFromElephant = elephantMinutesOpened * elephant.flowRate(elephantValve)
                def opened = new HashSet(player.openedValves) + valve + elephantValve
                return totalPressureFromElephant + findMaxPressureWithHelp(
                        player.next(valve, timeToValve, opened),
                        elephant.next(elephantValve, elephantTimeToValve, opened),
                        maxTime)
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
class State {
    String currentValve
    int timeElapsed
    Map<String, Map<String, Integer>> paths
    Set<String> workingValves
    Graph graph
    Set<String> openedValves

    static State start(Graph graph) {
        def workingValves = graph.nodes.findAll { it.value > 0}.keySet()
        return new State('AA', 0, graph.paths(), workingValves, graph, new HashSet<String>())
    }

    boolean hasOpened(String valve) {
        return openedValves.contains(valve)
    }

    int timeToMoveToValve(String valve) {
        return timeElapsed + paths[currentValve][valve] + 1
    }

    int flowRate(String valve) {
        graph.nodes[valve]
    }

    State next(String next, int timeElapsed) {
        return new State(next, timeElapsed, paths, workingValves, graph, new HashSet(openedValves) + next)
    }

    State next(String next, int timeElapsed, Set<String> opened) {
        return new State(next, timeElapsed, paths, workingValves, graph, opened)
    }
}

@EqualsAndHashCode
@TupleConstructor(includeFields = true)
class Graph {
    Map<String, Integer> nodes = [:]
    Map<String, List<String>> edges = [:]

    Map<String, Map<String, Integer>> paths() {
       nodes.keySet().collectEntries{ [(it): getShortestPaths(it)]}
    }

    def getShortestPaths(String source) {
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