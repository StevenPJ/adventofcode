package com.adventofcode.common;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.List;

interface Selector {

    List<Solution> solutions();

    @Value
    class All implements Selector {

        @Override
        public List<Solution> solutions() {
            Reflections reflections = new Reflections("com.adventofcode");
            return reflections.getSubTypesOf(Solution.class)
                    .stream()
                    .map(clazz -> {
                        try {
                            return (Solution) clazz.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sorted()
                    .toList();
        }
    }

    @Value
    class Last implements Selector {

        @Override
        public List<Solution> solutions() {
            var all = new Selector.All().solutions();
            return Collections.singletonList(all.get(all.size() - 1));
        }
    }

    @Value
    @AllArgsConstructor
    class One implements Selector {
        int year;
        int day;

        public One(String year, String day) {
            this.year = Integer.parseInt(year);
            this.day = Integer.parseInt(day);
        }

        @Override
        public List<Solution> solutions() {
            var matches = new Selector.All().solutions().stream()
                    .filter(solution -> solution.matches(this.year, this.day))
                    .toList();
            assert matches.size() == 1;
            return matches;
        }
    }

}
