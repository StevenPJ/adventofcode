package com.adventofcode.aoc2022

import spock.lang.Specification

class Day7Spec extends Specification {

    def example = '''\
    $ cd /
    $ ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    $ cd a
    $ ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    $ cd e
    $ ls
    584 i
    $ cd ..
    $ cd ..
    $ cd d
    $ ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
    '''.stripIndent()

    def "part 1 example"() {

        expect:
        new Day7().part1(example) == 95437
    }

    def "part 2 example"() {

        expect:
        new Day7().part2(example) == 24933642
    }

}
