package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day04 extends DayHandler {
    private final static HashMap<String, Integer> WEIGHTS = new HashMap<>() {{
        put("X", 1);
        put("M", 2);
        put("A", 4);
        put("S", 8);
    }};

    public Day04() {
        super("04");
        testValues = new Object[]{18, 9};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        List<Integer[][]> data = new ArrayList<>();
        data.add(ArrayUtils.mappedMatrix(input, WEIGHTS));

        data.add(ArrayUtils.rotated(data.get(0)));
        data.add(ArrayUtils.diagonal(data.get(0)));
        data.add(ArrayUtils.diagonal(data.get(1)));

        return data.stream().mapToInt((matrix) ->
                Arrays.stream(matrix).mapToInt((row) -> {
                    int result = 0;
                    for (int i = 0; i < row.length - 3; i++) {
                        if ((row[i] > row[i + 1] && row[i + 1] > row[i + 2] && row[i + 2] > row[i + 3]) ||
                                (row[i] < row[i + 1] && row[i + 1] < row[i + 2] && row[i + 2] < row[i + 3])) {
                            result++;
                        }
                    }
                    return result;
                }).sum()
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        Integer[][] values = ArrayUtils.mappedMatrix(input, WEIGHTS);

        int result = 0;

        for (int i = 1; i < values.length - 1; i++) {
            for (int j = 1; j < values.length - 1; j++) {
                if (values[i][j] != 4)
                    continue;

                if (values[i - 1][j - 1] + values[i + 1][j + 1] == 10 && values[i + 1][j - 1] + values[i - 1][j + 1] == 10)
                    result++;
            }
        }
        return result;
    }
}
