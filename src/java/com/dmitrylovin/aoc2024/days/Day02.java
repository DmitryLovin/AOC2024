package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.ArrayUtils;
import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.Arrays;
import java.util.Iterator;

public class Day02 extends DayHandler {
    private final String separator = " ";

    private final String[] input;
    private final String[] testInput;

    public Day02() {
        input = FileUtils.parseInput("02");
        testInput = FileUtils.parseTestInput("02");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        return Arrays.stream(input).mapToInt((row) ->
                isValid(
                        Arrays.stream(row.split(separator)).mapToInt(Integer::parseInt).toArray()
                ) ? 1 : 0).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        return Arrays.stream(input).mapToInt((row) -> {
            int[] values = Arrays.stream(row.split(separator)).mapToInt(Integer::parseInt).toArray();

            if (isValid(values))
                return 1;
            else {
                for (int i = 0; i < values.length; i++) {
                    if (isValid(ArrayUtils.remove(values, i)))
                        return 1;
                }
            }
            return 0;
        }).sum();
    }

    private boolean isValid(int[] values) {
        Iterator<Integer> iterator = Arrays.stream(values).iterator();

        int first = iterator.next();
        int second = iterator.next();

        if (first == second || Math.abs(first - second) > 3)
            return false;

        boolean increase = second > first;
        first = second;

        while (iterator.hasNext()) {
            second = iterator.next();
            if (invalidValue(first, second, increase))
                return false;
            first = second;
        }
        return true;
    }

    private boolean invalidValue(int first, int second, boolean increase) {
        if (first == second)
            return true;
        int diff = second - first;
        if (increase && diff < 0 || diff > 3)
            return true;
        if (!increase && diff > 0 || diff < -3)
            return true;
        return false;
    }
}
