package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.utils.ArrayUtils;
import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.*;

public class Day04 extends DayHandler {
    private final String[] input;
    private final String[] testInput;

    HashMap<String, String> CHARS = new HashMap<>();
    HashSet<String> MAS = new HashSet<>();

    public Day04() {
        input = FileUtils.parseInput("04");
        testInput = FileUtils.parseTestInput("04");
        CHARS.put("M", "X");
        CHARS.put("A", "M");
        CHARS.put("S", "A");
        MAS.add("MAS");
        MAS.add("SAM");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        List<String[][]> data = new ArrayList<>();
        data.add(ArrayUtils.splitChars(input));

        data.add(ArrayUtils.rotated(data.get(0)));
        data.add(ArrayUtils.diagonal(data.get(0)));
        data.add(ArrayUtils.diagonal(data.get(1)));

        return data.stream().mapToInt((matrix) ->
            Arrays.stream(matrix).mapToInt((row) -> {
                String[] reverseRow = ArrayUtils.reversed(row);
                return line(row) + line(reverseRow);
            }).sum()
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        String[][] rows = ArrayUtils.splitChars(input);

        int result = 0;

        for (int i = 1; i < rows.length - 1; i++) {
            for (int j = 1; j < rows.length - 1; j++) {
                String[] chars = new String[]{rows[i - 1][j - 1],rows[i][j],rows[i + 1][j + 1]};
                String[] chars2 = new String[]{rows[i + 1][j - 1],rows[i][j],rows[i - 1][j + 1]};
                String word = String.join("",chars);
                String word2 = String.join("",chars2);

                if(MAS.contains(word) && MAS.contains(word2))
                    result++;
            }
        }
        return result;
    }

    private int line(String[] line) {
        String prev = "";
        int result = 0;
        Iterator<String> iterator = Arrays.stream(line).iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (Objects.equals(CHARS.get(next), prev)) {
                prev = next;
                if (next.equals("S"))
                    result++;
            } else if (next.equals("X")) {
                prev = next;
            } else {
                prev = "";
            }
        }
        return result;
    }
}
