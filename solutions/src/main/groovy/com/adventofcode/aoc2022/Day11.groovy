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
        def inspections = [0] * monkeys.size()
        nRounds.times {
            monkeys.eachWithIndex { monkey, index ->
                inspections[index] += monkey.items.size()
                monkey.takeTurn(monkeys, worryReductionFunction)
            }
        }
       return inspections.sort{-it}.take(2).inject(1) { a, b -> a * b as Long}
    }
}

class Monkey {
    List<Long> items
    Closure<Long> operation
    Integer testValue, throwToWhenTrue, throwToWhenFalse

    static def operators = [
            "+":  { factor, item -> item + factor.call(item) },
            "-":  { factor, item -> item - factor.call(item) },
            "*":  { factor, item -> item * factor.call(item) },
            "/":  { factor, item -> item / factor.call(item) },
    ]

    Monkey(String input) {
        def monkeySpec =  input
                .replace("Starting items: ", "")
                .replace("Operation: new = old ", "")
                .replace("Test: divisible by ", "")
                .replace("If true: throw to monkey ", "")
                .replace("If false: throw to monkey ", "")
                .split("\n")
                .findAll { !it.isAllWhitespace()}
                .takeRight(5)
                .collect{ it.trim()}
        this.items = monkeySpec[0].tokenize (",").collect{ Long.parseLong(it.trim())}
        def operationSpec = monkeySpec[1].tokenize()
        def factor = operationSpec.last() == 'old' ? null : Long.parseLong(operationSpec.last())
        this.operation = operators.get(operationSpec.first()).curry({ item -> factor ?: item })
        this.testValue = Integer.parseInt(monkeySpec[2])
        this.throwToWhenTrue = Integer.parseInt(monkeySpec[3])
        this.throwToWhenFalse = Integer.parseInt(monkeySpec[4])
    }

    def takeTurn(List<Monkey> monkeys, Closure<Long> worryLevelReduction) {
        items.size().times {
            def inspecting = items.pop()
            def newWorryLevel = operation.call(inspecting)
            def managedWorryLevel = worryLevelReduction.call(newWorryLevel)
            def throwTo = managedWorryLevel % testValue == 0 ? throwToWhenTrue : throwToWhenFalse
            monkeys[throwTo].items << managedWorryLevel
        }
    }
}