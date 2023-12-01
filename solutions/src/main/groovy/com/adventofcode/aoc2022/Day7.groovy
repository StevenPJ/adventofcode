package com.adventofcode.aoc2022

import com.adventofcode.Solution


class Day7 extends Solution {


    @Override
    def part1(String input) {
        def root = buildFileSystem(input)
        return root.getSubDirectorySizes().findAll{ it <= 100000}.sum() as Long
    }

    @Override
    def part2(String input) {
        def root = buildFileSystem(input)
        def spaceNeeded = 30000000
        def unused = 70000000 - root.fileSize()
        def spaceRequired = spaceNeeded - unused

        return root.getSubDirectorySizes().sort().find{ it >= spaceRequired}
    }

    static buildFileSystem(String input) {
        Directory root = new Directory("/")
        Directory crawler

        input.split("\n").each {
            if (it == '$ cd /') {
                crawler = root
            } else if (it == '$ cd ..') {
                crawler = crawler.parent
            } else if (it.startsWith('$ cd')) {
                def dir = new Directory(it.replace('$ cd ', ''))
                if (crawler != null) {
                    crawler.add(dir)
                }
                crawler = dir
            } else if (!it.startsWith('$ ls') && !it.startsWith('dir')) {
                def sizeAndName = it.tokenize()
                crawler.add(new File(sizeAndName[1], sizeAndName[0] as Long))
            }
        }
        return root
    }
}

abstract class Component {
    private Directory parent

    Directory getParent() {
        return parent
    }

    void setParent(Directory parent) {
        this.parent = parent
    }

    abstract Long fileSize()
}

class File extends Component {
    Long size
    String name

    File(String name, Long size) {
        this.name = name
        this.size = size
    }

    @Override
    Long fileSize() {
        return size
    }
}

class Directory extends Component {
    List<Component> components
    String name

    Directory(String name) {
        this.name = name
        this.components = []
    }

    void add(Component component) {
        component.setParent(this)
        this.components.add(component)
    }

    List<Long> getSubDirectorySizes() {
        def sizes = []
        sizes.add(this.fileSize())
        components.forEach{
            if (it instanceof Directory) {
                sizes.addAll(it.getSubDirectorySizes())
            }
        }
        return sizes
    }

    @Override
    Long fileSize() {
        components.collect { it.fileSize() }.sum() as Long
    }
}