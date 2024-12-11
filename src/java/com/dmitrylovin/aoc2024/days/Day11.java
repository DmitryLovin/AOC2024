package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.NumberUtils;

import java.util.Arrays;
import java.util.HashMap;

public class Day11 extends DayHandler {
    HashMap<Long, Long>[] cache;
    int size;

    public Day11() {
        super("11");
        testValues = new Object[]{55312L, 65601038650482L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initCache(24);
        return Arrays.stream(input[0].split(" ")).mapToLong((v) ->
                length(Long.parseLong(v), 0)
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initCache(74);
        return Arrays.stream(input[0].split(" ")).mapToLong((v) ->
                length(Long.parseLong(v), 0)
        ).sum();
    }

    private void initCache(int size) {
        this.size = size;
        cache = new HashMap[size];
        for (int i = 0; i < size; i++) {
            cache[i] = new HashMap<>();
        }
    }

    private long length(long value, int index) {
        int length = NumberUtils.lengthLong(value);
        if (index == size)
            return NumberUtils.lengthLong(value) % 2 == 0 ? 2L : 1L;
        if (cache[index].containsKey(value))
            return cache[index].get(value);
        if (length % 2 == 0) {
            int pow = (int) Math.pow(10, (length / 2));
            cache[index].put(value, length(value / pow, index + 1) + length(value % pow, index + 1));
        } else {
            cache[index].put(value, length(value == 0 ? 1 : value * 2024L, index + 1));
        }
        return cache[index].get(value);
    }
}
