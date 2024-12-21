package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;
import com.dmitrylovin.aoc2024.models.Vector;

import java.util.*;

public class Day20 extends DayHandler {
    List<Position> map;

    public Day20() {
        super("20");
        testValues = new Object[]{5, 285};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        return getResult(isTestRun ? 20 : 100, 2, map.size());
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        return getResult(isTestRun ? 50 : 100, 20, map.size());
    }

    private int getResult(int threshold, int maxDist, int size) {
        int result = 0;
        int maxI = size - threshold;
        for (int i = 0; i < maxI; i++) {
            Position currentPosition = map.get(i);
            for (int j = i + threshold; j < size; j++) {
                Position check = map.get(j);
                int dist = check.distance(currentPosition);
                if (dist > maxDist)
                    continue;
                if (j - i - dist >= threshold)
                    result += 1;
            }
        }
        return result;
    }

    private void initMap(Iterator<String> iterator) {
        Vector start = new Vector(0, 0, 0, 1);
        Position finish = new Position(0, 0);

        Set<Position> spaces = new HashSet<>();
        int y = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] items = line.split("");
            for (int x = 0; x < items.length; x++) {
                if (items[x].equals("."))
                    spaces.add(new Position(x, y));
                else if (items[x].equals("E")) {
                    spaces.add(new Position(x, y));
                    finish = new Position(x, y);
                } else if (items[x].equals("S")) {
                    spaces.add(new Position(x, y));
                    start = new Vector(x, y, 1, 0);
                }
            }
            y++;
        }

        map = new ArrayList<>();
        while (!start.pos().equals(finish)) {
            map.add(start.pos());

            if (!spaces.contains(start.copy().move().pos())) {
                if (spaces.contains(start.copy().rotate().move().pos()))
                    start.rotate();
                else
                    start.rotateBack();
            }

            start.move();
        }
        map.add(finish);
    }
}
