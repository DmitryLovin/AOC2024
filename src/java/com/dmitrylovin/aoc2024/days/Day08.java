package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;

import java.util.*;

public class Day08 extends DayHandler {
    HashMap<String, List<Position>> ANTENNAS;
    HashMap<Position, String> POSITIONS;

    public Day08() {
        super("08");
        testValues = new Object[]{14, 34};
    }

    @Override
    Object partOne(boolean isTestRun) {
        ANTENNAS = new HashMap<>();
        POSITIONS = new HashMap<>();

        String[] input = isTestRun ? testInput : this.input;

        int height = input.length;
        int width = input[0].length();
        initPositions(input, width, height);

        HashSet<Position> results = new HashSet<>();
        for (Map.Entry<String, List<Position>> entry : ANTENNAS.entrySet()) {
            List<Position> antennas = entry.getValue();
            int size = antennas.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    Position check = antennas.get(i).copy();
                    Position checkSub = antennas.get(j).copy();
                    Position delta = check.copy().sub(checkSub);

                    if (check.add(delta).inBorder(width, height) && !entry.getKey().equals(POSITIONS.get(check))) {
                        results.add(check);
                    }
                    if (checkSub.sub(delta).inBorder(width, height) && !entry.getKey().equals(POSITIONS.get(checkSub))) {
                        results.add(checkSub);
                    }
                }
            }
        }
        return results.size();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        ANTENNAS = new HashMap<>();
        POSITIONS = new HashMap<>();

        String[] input = isTestRun ? testInput : this.input;


        int height = input.length;
        int width = input[0].length();
        initPositions(input, width, height);

        HashSet<Position> results = new HashSet<>(POSITIONS.keySet());
        for (Map.Entry<String, List<Position>> entry : ANTENNAS.entrySet()) {
            List<Position> antennas = entry.getValue();
            int size = antennas.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    Position check = antennas.get(i).copy();
                    Position checkSub = antennas.get(j).copy();
                    Position delta = check.copy().sub(checkSub);

                    check.add(delta);
                    checkSub.sub(delta);

                    while (check.inBorder(width, height)) {
                        results.add(check.copy());
                        check.add(delta);
                    }
                    while (checkSub.inBorder(width, height)) {
                        results.add(checkSub.copy());
                        checkSub.sub(delta);
                    }
                }
            }
        }
        return results.size();
    }

    private void initPositions(String[] input, int width, int height) {
        for (int y = 0; y < height; y++) {
            String[] line = input[y].split("");
            for (int x = 0; x < width; x++) {
                if (line[x].equals("."))
                    continue;
                POSITIONS.put(new Position(x, y), line[x]);
                if (ANTENNAS.get(line[x]) instanceof List<Position> list) {
                    list.add(new Position(x, y));
                } else {
                    List<Position> list = new ArrayList<>();
                    list.add(new Position(x, y));
                    ANTENNAS.put(line[x], list);
                }
            }
        }
    }
}
