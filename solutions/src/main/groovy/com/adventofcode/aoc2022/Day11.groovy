package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day11 extends Solution {

    @Override
    def part1(String input) {
        def monkeys = input.split("\n\n").collect { new Monkey(it)}
        def worryReduction = { Long item -> Math.floor(item / 3) as Long}
        return getMonkeyBusinessAfterNRounds(monkeys, 20, worryReduction)
    }

    @Override
    def part2(String input) {
        def monkeys = input.split("\n\n").collect { new Monkey(it)}

        // where y is a factor of z
        // x % y == (x % z) % y
        // so we store (x % z) where all monkey testValues are a factor of z
        def z = monkeys.collect{it.testValue}.inject(1) { a,b -> a * b}
        def worryReduction = { Long x ->  x % z}
        return getMonkeyBusinessAfterNRounds(monkeys, 10000, worryReduction)
    }

    static getMonkeyBusinessAfterNRounds(List<Monkey> monkeys, int nRounds, Closure<Long> worryReductionFunction) {
        def rounds = (1..nRounds).collect {
            monkeys.collect { it.takeTurn(monkeys, worryReductionFunction) }
        }
        return getMonkeyBusiness(rounds)
    }

    static getMonkeyBusiness(def roundResults) {
        roundResults
                .transpose()
                .collect {it.sum()}
                .sort()
                .reverse()
                .take(2)
                .inject(1) { a, b -> a * b as Long}
    }
}


class Monkey {
    List<Long> items
    Closure<Long> operation
    Integer testValue
    Integer throwToWhenTrue
    Integer throwToWhenFalse

    static def operators = [
            "+":  { factor, item -> item + factor.call(item) },
            "-":  { factor, item -> item - factor.call(item) },
            "*":  { factor, item -> item * factor.call(item) },
            "/":  { factor, item -> item / factor.call(item) },
    ]

    Monkey(String input) {
        input.split("\n").each {
            if (it.contains("Starting items:"))
                this.items = it.replace("Starting items:", "").split(",").collect{ Long.parseLong(it.trim())}
            if (it.contains("Operation:")) {
                def function = it.replace("Operation: new = old ", "").tokenize()
                this.operation = operators.get(function[0]).curry({ item -> function[1] == 'old' ? item : Long.parseLong(function[1]) })
            }
            if (it.contains("Test:"))
                this.testValue = Integer.parseInt(it.tokenize().last())
            if (it.contains("If true:"))
                this.throwToWhenTrue = Integer.parseInt(it.tokenize().last())
            if (it.contains("If false:"))
                this.throwToWhenFalse = Integer.parseInt(it.tokenize().last())
        }
    }

    int takeTurn(List<Monkey> monkeys, Closure<Long> worryLevelReduction) {
        def nInspections = items.size()
        nInspections.times {
            def inspecting = items.pop()
            def newWorryLevel = operation.call(inspecting)
            def managedWorryLevel = worryLevelReduction.call(newWorryLevel)
            def throwTo = managedWorryLevel % testValue == 0 ? throwToWhenTrue : throwToWhenFalse
            monkeys[throwTo].items << managedWorryLevel
        }
        return nInspections
    }
}