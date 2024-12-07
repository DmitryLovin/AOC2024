package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class Day03 extends DayHandler {
    private final String[] input;
    private final String[] testInput;
    private final Pattern PATTERN = Pattern.compile("mul\\(([0-9]{1,3},[0-9]{1,3})\\)");
    private final Pattern PATTERN2 = Pattern.compile("(don't\\(\\))|(do\\(\\))|(mul\\([0-9]{1,3},[0-9]{1,3}\\))");

    public Day03() {
        input = FileUtils.parseInput("03");
        testInput = FileUtils.parseTestInput("03");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        return Arrays.stream(input).parallel().mapToInt((row) ->
                PATTERN.matcher(row).results().mapToInt((matchResult) -> {
                    Iterator<Integer> iterator = Arrays.stream(matchResult.group(1).split(",")).mapToInt(Integer::parseInt).iterator();
                    return iterator.next() * iterator.next();
                }).sum()
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        AtomicBoolean enabled = new AtomicBoolean(true);

        return Arrays.stream(input).mapToInt((row) -> PATTERN2.matcher(row).results().mapToInt((matchResult) -> {
            switch (matchResult.group()) {
                case "don't()" -> {
                    enabled.set(false);
                    return 0;
                }
                case "do()" -> {
                    enabled.set(true);
                    return 0;
                }
                default -> {
                    Iterator<Integer> iterator = Arrays.stream(
                            matchResult.group().replaceAll("[mul()]?", "").split(",")
                    ).mapToInt(Integer::parseInt).iterator();
                    return enabled.get() ? iterator.next() * iterator.next() : 0;
                }
            }
        }).sum()).sum();
    }
}
