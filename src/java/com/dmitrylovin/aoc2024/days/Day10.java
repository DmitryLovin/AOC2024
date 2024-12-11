package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day10 extends DayHandler {

    Set<Position> starts;
    HashMap<Position, Integer> map;
    int width;
    int height;

    final Position[] dirs = new Position[]{
            new Position(0, 1),
            new Position(0, -1),
            new Position(1, 0),
            new Position(-1, 0)
    };

    public Day10() {
        super("10");
        testValues = new Object[]{36, 81};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initMap(input);

        return starts.stream().mapToInt(
                (start) -> throughMap(start, new HashSet<>(), new HashMap<>()).size()
        ).sum();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initMap(input);

        return starts.stream().mapToInt(
                (start) ->
                        throughMap(start, new HashSet<>(), new HashMap<>())
                                .values().stream().mapToInt(Integer::intValue).sum()
        ).sum();
    }

    private void initMap(String[] input) {
        map = new HashMap<>();
        starts = new HashSet<>();
        width = input[0].length();
        height = input.length;

        for (int y = 0; y < input.length; y++) {
            int[] row = Arrays.stream(input[y].split("")).mapToInt(Integer::parseInt).toArray();
            for (int x = 0; x < row.length; x++) {
                map.put(new Position(x, y), row[x]);
                if (row[x] == 0) {
                    starts.add(new Position(x, y));
                }
            }
        }
    }

    private HashMap<Position, Integer> throughMap(Position position, Set<Position> route, HashMap<Position, Integer> finishes) {
        route.add(position);

        for (Position dir : dirs) {
            Position newPos = position.copy().add(dir);
            if (!route.contains(newPos) && newPos.inBorder(width, height)) {
                Integer slope = map.get(newPos);
                if (slope - map.get(position) == 1) {
                    if (slope == 9) {
                        finishes.merge(newPos, 1, Integer::sum);
                    } else {
                        throughMap(newPos, new HashSet<>(route), finishes);
                    }
                }
            }
        }

        return finishes;
    }
}
