package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends DayHandler {
    private final String[] input;
    private final String[] testInput;
    private final String PATTERN = "mul\\(([0-9]{1,3},[0-9]{1,3})\\)";
    private final String PATTERN2 = "(don't\\(\\))|(do\\(\\))|(mul\\([0-9]{1,3},[0-9]{1,3}\\))";

    public Day03() {
        input = FileUtils.parseInput("03");
        testInput = FileUtils.parseTestInput("03");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        return Arrays.stream(input).mapToInt((row) -> {
                    Pattern pattern = Pattern.compile(PATTERN);
                    Matcher matcher = pattern.matcher(row);
                    int result = 0;
                    while (matcher.find()) {
                        Iterator<Integer> iterator = Arrays.stream(matcher.group(1).split(",")).mapToInt(Integer::parseInt).iterator();
                        result += iterator.next() * iterator.next();
                    }

                    return result;
                }
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        boolean enabled = true;
        int result = 0;
        for (String row : input) {
            Pattern pattern = Pattern.compile(PATTERN2);
            Matcher matcher = pattern.matcher(row);
            while (matcher.find()) {
                if (Objects.equals(matcher.group(0), "don't()")) {
                    enabled = false;
                } else if (Objects.equals(matcher.group(0), "do()")) {
                    enabled = true;
                } else if (enabled) {
                    Iterator<Integer> iterator = Arrays.stream(matcher.group().replaceAll("[mul()]?", "").split(",")).mapToInt(Integer::parseInt).iterator();
                    result += iterator.next() * iterator.next();
                }
            }
        }
        return result;
    }
}
