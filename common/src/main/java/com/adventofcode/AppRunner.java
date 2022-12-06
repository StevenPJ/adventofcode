package com.adventofcode;

public class AppRunner {

    public static void run(String[] args) {
        var parser = new Parser();
        var runner = new Runner(new Display(), new Timer());

        var which = parser.parse(args);
        runner.runSolutions(which);
    }
}
