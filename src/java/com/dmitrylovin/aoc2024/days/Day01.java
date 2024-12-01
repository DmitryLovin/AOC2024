package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.IntPair;
import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.*;

public class Day01 extends DayHandler {
    private final String separator = " {3}";

    private final String[] input;
    private final String[] testInput;

    public Day01() {
        input = FileUtils.parseInput("01");
        testInput = FileUtils.parseTestInput("01");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        List<IntPair> pairs = Arrays.stream(input).map((row) -> {
            Iterator<Integer> values = Arrays.stream(row.split(separator)).mapToInt(Integer::parseInt).iterator();
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
            Iterator<Integer> values = Arrays.stream(row.split(separator)).mapToInt(Integer::parseInt).iterator();
            leftList.add(values.next());
            rightList.merge(values.next(), 1, Integer::sum);
        });

        return leftList.stream().mapToInt((value) -> value * rightList.getOrDefault(value, 0)).sum();
    }
}
