package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.IntPair;

import java.util.*;

public class Day01 extends DayHandler {
    private final static String SEPARATOR = " {3}";

    public Day01() {
        super("01");
        testValues = new Object[]{11, 31};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        List<IntPair> pairs = Arrays.stream(input).parallel().map((row) -> {
            Iterator<Integer> values = Arrays.stream(row.split(SEPARATOR)).mapToInt(Integer::parseInt).iterator();
            return new IntPair(values.next(), values.next());
        }).toList();

        Iterator<Integer> rightList = pairs.stream().mapToInt(IntPair::right).sorted().iterator();

        return pairs.stream().sorted(Comparator.comparingInt(IntPair::left))
                .mapToInt((value) -> Math.abs(value.left() - rightList.next()))
                .sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        List<Integer> leftList = new ArrayList<>();
        HashMap<Integer, Integer> rightList = new HashMap<>();
        Arrays.stream(input).forEach((row) -> {
            Iterator<Integer> values = Arrays.stream(row.split(SEPARATOR)).mapToInt(Integer::parseInt).iterator();
            leftList.add(values.next());
            rightList.merge(values.next(), 1, Integer::sum);
        });

        return leftList.stream().mapToInt((value) -> value * rightList.getOrDefault(value, 0)).sum();
    }
}
