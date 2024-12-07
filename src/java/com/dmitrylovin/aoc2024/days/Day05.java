package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.ArrayUtils;

import java.util.*;
import java.util.stream.StreamSupport;

public class Day05 extends DayHandler {
    private HashMap<Integer, Set<Integer>> MAP;

    public Day05() {
        super("05");
        testValues = new Object[]{143, 123};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        Iterable<String> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), false).mapToInt((row) -> {
            int[] items = Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray();
            for (int i = items.length - 1; i >= 0; i--) {
                if (MAP.get(items[i]) instanceof Set<Integer> set) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (set.contains(items[j])) {
                            return 0;
                        }
                    }
                }
            }
            return items[(items.length - 1) / 2];
        }).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        Iterable<String> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), false).parallel().mapToInt((row) -> {
            int[] items = Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray();
            for (int i = items.length - 1; i >= 0; i--) {
                if (MAP.get(items[i]) instanceof Set<Integer> set) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (set.contains(items[j])) {
                            return sortFromAndGet(items, i, (items.length - 1) / 2);
                        }
                    }
                }
            }
            return 0;
        }).sum();
    }

    private void initMap(Iterator<String> iterator) {
        MAP = new HashMap<>();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.isEmpty())
                break;
            Iterator<Integer> mapItems = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).iterator();
            Integer key = mapItems.next();
            if (MAP.containsKey(key)) {
                MAP.get(key).add(mapItems.next());
            } else {
                MAP.put(key, new HashSet<>() {{
                    add(mapItems.next());
                }});
            }
        }
    }

    private int sortFromAndGet(int[] items, int from, int to) {
        for (int i = from; i >= to; i--) {
            if (MAP.get(items[i]) instanceof Set<Integer> set) {
                for (int j = i - 1; j >= 0; j--) {
                    if (set.contains(items[j])) {
                        ArrayUtils.moveDown(items, i, j);
                        i++;
                        break;
                    }
                }
            }
        }
        return items[(items.length - 1) / 2];
    }
}
