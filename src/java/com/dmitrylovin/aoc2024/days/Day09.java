package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day09 extends DayHandler {

    public Day09() {
        super("09");
        testValues = new Object[]{1928L, 2858L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String input = isTestRun ? testInput[0] : this.input[0];
        int[] data = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();
        Integer[] raw = new Integer[Arrays.stream(data).sum()];
        int point = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i]; j++) {
                raw[point + j] = i % 2 == 0 ? i / 2 : null;

            }
            point += data[i];
        }
        sort(raw);
        long result = 0L;
        for (int i = 0; i < raw.length; i++) {
            if (raw[i] == null)
                break;
            result += (long) i * raw[i];
        }
        return result;
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput[0].split("") : this.input[0].split("");
        Fragment[] data = new Fragment[input.length];
        Set<Integer> sizes = new HashSet<>();
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                data[i] = new Fragment(Integer.parseInt(input[i]), i / 2);
                sizes.add(data[i].size);
            } else {
                data[i] = new Fragment(Integer.parseInt(input[i]));
            }
        }

        long multiplier = 0L;
        long result = 0L;
        for (int i = 0; i < data.length; i++) {
            if (!data[i].filled) {
                int size = data[i].size;
                if (sizes.stream().anyMatch((s) -> size >= s)) {
                    for (int j = data.length - 1; j > i; j--) {
                        if (data[j].filled && data[j].size <= data[i].size) {
                            int diff = data[i].size - data[j].size;
                            data[i] = data[j];
                            data[j] = new Fragment(data[i].size);
                            if (diff > 0) {
                                data = ArrayUtils.insert(data, new Fragment(diff), i + 1);
                            }
                            break;
                        }
                    }
                    if (!data[i].filled) {
                        sizes.removeIf((s) -> s <= size);
                    }
                }
            }
            if (data[i].filled)
                result += data[i].sum(multiplier);
            multiplier += data[i].size;
        }
        return result;
    }

    private void sort(Integer[] array) {
        int index = array.length - 1;
        int point = 0;
        while (index > point) {
            if (array[index] == null) {
                index--;
                continue;
            }
            if (array[point] != null) {
                point++;
                continue;
            }
            array[point] = array[index];
            array[index] = null;
        }
    }

    class Fragment {
        int size;
        int index;
        boolean filled;

        public Fragment(int size) {
            this.size = size;
            filled = false;
        }

        public Fragment(int size, int index) {
            this.size = size;
            this.index = index;
            filled = true;
        }

        public long sum(long index) {
            if (size > 1) {
                int m = size - 1;
                return (this.index * (index * size + ((m * m + m) / 2)));
            } else {
                return this.index * index;
            }
        }
    }
}
