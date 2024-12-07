package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.FileUtils;
import com.dmitrylovin.aoc2024.utils.NumberUtils;

import java.util.Arrays;

public class Day07 extends DayHandler {
    private final String[] input;
    private final String[] testInput;

    public Day07() {
        input = FileUtils.parseInput("07");
        testInput = FileUtils.parseTestInput("07");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        return Arrays.stream(input).parallel().mapToLong((row) -> {
            String[] parts = row.split(": ");
            long value = Long.parseLong(parts[0]);
            return compute(
                    value,
                    0,
                    Arrays.stream(parts[1].split(" ")).mapToInt(Integer::parseInt).toArray(),
                    0,
                    true
            ) ? value : 0;
        }).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        return Arrays.stream(input).parallel().mapToLong((row) -> {
            String[] parts = row.split(": ");
            long value = Long.parseLong(parts[0]);
            return computeV2(
                    value,
                    0,
                    Arrays.stream(parts[1].split(" ")).mapToInt(Integer::parseInt).toArray(),
                    0,
                    Action.SUM
            ) ? value : 0;
        }).sum();
    }

    private boolean compute(long compare, long current, int[] values, int index, boolean sum) {
        if (current > compare)
            return false;

        long newCurrent = sum ? current + values[index] : current * values[index];

        return index == values.length - 1
                ? newCurrent == compare
                : compute(compare, newCurrent, values, index + 1, true)
                || compute(compare, newCurrent, values, index + 1, false);
    }

    private boolean computeV2(long compare, long current, int[] values, int index, Action sum) {
        if (current > compare)
            return false;

        long newCurrent = switch (sum) {
            case SUM -> current + values[index];
            case MUL -> current * values[index];
            case ADD -> current * NumberUtils.power(values[index]) + values[index];
        };

        return index == values.length - 1
                ? newCurrent == compare
                : computeV2(compare, newCurrent, values, index + 1, Action.SUM)
                || computeV2(compare, newCurrent, values, index + 1, Action.MUL)
                || computeV2(compare, newCurrent, values, index + 1, Action.ADD);
    }

    enum Action {
        SUM,
        MUL,
        ADD
    }
}
