package com.adventofcode;


class Parser {
    public Selector parse(String[] args) {
        if (args.length > 2)
            throw new RuntimeException("Too many arguments");

        if (args.length == 0)
            return new Selector.All();

        if (args.length == 2)
            return new Selector.One(args[0], args[1]);

        if (args[0].equals("last"))
            return new Selector.Last();

        return new Selector.All();
    }
}
