package com.dmitrylovin.aoc2024.days;

import java.util.*;
import java.util.stream.StreamSupport;

public class Day19 extends DayHandler {
    HashSet<String> deadPatterns;
    HashMap<String, Long> finishedPatterns;

    public Day19() {
        super("19");
        testValues = new Object[]{8, 37L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        String[] towels = iterator.next().split(", ");

        iterator.next();
        Iterable<String> iterable = () -> iterator;

        deadPatterns = new HashSet<>();

        return StreamSupport.stream(iterable.spliterator(), false).mapToInt(
                (pattern) -> findTowels(pattern, Arrays.stream(towels).filter(pattern::contains).toList()) ? 1 : 0
                ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        String[] towels = iterator.next().split(", ");

        iterator.next();
        Iterable<String> iterable = () -> iterator;

        deadPatterns = new HashSet<>();
        finishedPatterns = new HashMap<>();

        return StreamSupport.stream(iterable.spliterator(), false).mapToLong(
                (pattern) -> countWays(pattern, Arrays.stream(towels).filter(pattern::contains).toList(), 0L)
        ).sum();
    }

    private boolean findTowels(String pattern, List<String> towels) {
        List<String> usable = towels.stream().filter(pattern::startsWith).toList();
        for (String towel : usable) {
            String newPattern = pattern.substring(towel.length());
            if (deadPatterns.contains(newPattern))
                continue;
            if (newPattern.isEmpty())
                return true;
            if (findTowels(newPattern, towels))
                return true;
        }
        deadPatterns.add(pattern);
        return false;
    }

    private long countWays(String pattern, List<String> towels, long result) {
        List<String> usable = towels.stream().filter(pattern::startsWith).toList();

        long init = result;
        for (String towel : usable) {
            String newPattern = pattern.substring(towel.length());
            if (deadPatterns.contains(newPattern)) continue;
            if (newPattern.isEmpty()) {
                result += 1;
                continue;
            }
            if (finishedPatterns.containsKey(newPattern)) {
                result += finishedPatterns.get(newPattern);
                continue;
            }
            result = countWays(newPattern, towels, result);
        }
        if (result == 0) deadPatterns.add(pattern);
        else finishedPatterns.put(pattern, result - init);
        return result;
    }
}
