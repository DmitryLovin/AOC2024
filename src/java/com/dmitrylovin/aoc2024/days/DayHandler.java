package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.enums.Color;
import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.function.Supplier;

public abstract class DayHandler {
    protected final String[] input;
    protected final String[] testInput;
    protected Object[] testValues;

    public DayHandler(String day) {
        input = FileUtils.parseInput(day);
        testInput = FileUtils.parseTestInput(day);
    }

    public void handle() {
        Object testPartOne = partOne(true);
        System.out.printf("P1 Test: %s\n", testResult(testPartOne, testValues[0]));
        if (!testPartOne.equals(testValues[0]))
            return;
        System.out.printf("P1: %s%s\n", Color.GREEN, bench(() -> partOne(false)));
        Object testPartTwo = partTwo(true);
        System.out.printf("P2 Test: %s\n", testResult(testPartTwo, testValues[1]));
        if (!testPartTwo.equals(testValues[1]))
            return;
        System.out.printf("P2: %s%s\n", Color.GREEN, bench(() -> partTwo(false)));
    }

    abstract Object partOne(boolean isTestRun);

    abstract Object partTwo(boolean isTestRun);

    private String bench(Supplier<Object> action) {
        Object result = action.get();
        long current = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            result = action.get();
        }
        double time = (System.nanoTime() - current) / 10000000.0d;
        return String.format("%s%s (%.3fms)", result, Color.RESET, time);
    }

    private String testResult(Object value, Object check) {
        return String.format(
                "%s (%s%s%s)",
                value,
                value.equals(check) ? Color.GREEN : Color.RED,
                value.equals(check) ? "PASS" : "FAIL", Color.RESET
        );
    }
}
