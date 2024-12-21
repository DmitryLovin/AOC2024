package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;
import com.dmitrylovin.aoc2024.models.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends DayHandler {
    HashMap<String, Position> digitPad = new HashMap<>() {{
        put("7", new Position(0, 0));
        put("8", new Position(1, 0));
        put("9", new Position(2, 0));
        put("4", new Position(0, 1));
        put("5", new Position(1, 1));
        put("6", new Position(2, 1));
        put("1", new Position(0, 2));
        put("2", new Position(1, 2));
        put("3", new Position(2, 2));
        put("0", new Position(1, 3));
        put("A", new Position(2, 3));
    }};

    HashMap<String, Position> dirPad = new HashMap<>() {{
        put("^", new Position(1, 0));
        put("A", new Position(2, 0));
        put("<", new Position(0, 1));
        put("v", new Position(1, 1));
        put(">", new Position(2, 1));
    }};

    HashMap<Position, String> paths = new HashMap<>() {{
        put(new Position(0, 1), "v");
        put(new Position(0, 2), "vv");
        put(new Position(0, 3), "vvv");
        put(new Position(0, -1), "^");
        put(new Position(0, -2), "^^");
        put(new Position(0, -3), "^^^");
        put(new Position(1, 0), ">");
        put(new Position(2, 0), ">>");
        put(new Position(-1, 0), "<");
        put(new Position(-2, 0), "<<");
    }};

    HashMap<Vector, Integer> complex;
    HashMap<Vector, String> dirPushes;

    List<HashMap<Vector, String>> dirMaps;

    HashMap<String, Long>[] cache;

    public Day21() {
        super("21");
        testValues = new Object[]{126384L, 154115708116294L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        init();

        return calculateComplexity(2, input);
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        init();

        return calculateComplexity(25, input);
    }

    private long calculateComplexity(int size, String[] input) {
        long result = 0;
        for (String code : input) {
            Position digitRobot = new Position(2, 3);
            Position[] robots = new Position[size];
            for (int i = 0; i < robots.length; i++) {
                robots[i] = new Position(2, 0);
            }

            String[] buttons = code.split("");
            long codeResult = 0;
            for (String button : buttons) {
                Position newRobotPosition = digitPad.get(button);
                long buttonResult = Long.MAX_VALUE;
                int cacheIndex = 0;
                for (HashMap<Vector, String> dp : dirMaps) {
                    dirPushes = dp;
                    for (String variant : getDigitVariants(digitRobot, newRobotPosition)) {
                        buttonResult = Math.min(nextRobot(robots, 0, variant, cacheIndex), buttonResult);
                    }
                    cacheIndex += 1;
                }
                codeResult += buttonResult;
                digitRobot = newRobotPosition;
            }
            result += codeResult * Integer.parseInt(code.replace("A", ""));
        }
        return result;
    }

    private long nextRobot(Position[] robots, int index, String path, int cacheIndex) {
        long result = 0;

        if (cache[index].containsKey(cacheIndex + path))
            return cache[index].get(cacheIndex + path);
        for (String p : path.split("")) {
            Position np = dirPad.get(p);
            if (index == robots.length - 1) {
                result += complex.get(new Vector(robots[index], np));
            } else {
                result += nextRobot(robots, index + 1, dirPushes.get(new Vector(robots[index], np)), cacheIndex);
            }
            robots[index] = np;
        }
        cache[index].put(cacheIndex + path, result);
        return result;
    }

    private void init() {
        complex = new HashMap<>();
        resetCache();

        for (Map.Entry<String, Position> entry : dirPad.entrySet()) {
            for (Map.Entry<String, Position> entry1 : dirPad.entrySet()) {
                Position f = entry.getValue();
                Position s = entry1.getValue();
                Vector key = new Vector(f, s);
                complex.put(key, f.distance(s) + 1);
            }
        }

        initDirMap();
    }

    private void resetCache() {
        cache = new HashMap[25];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new HashMap<>();
        }
    }

    private void initDirMap() {
        dirMaps = new ArrayList<>();
        dirMaps.add(new HashMap<>());
        for (Map.Entry<String, Position> leftEntry : dirPad.entrySet()) {
            for (Map.Entry<String, Position> rightEntry : dirPad.entrySet()) {
                Position left = leftEntry.getValue();
                Position right = rightEntry.getValue();
                Vector key = new Vector(left, right);
                for (HashMap<Vector, String> tmp : new ArrayList<>(dirMaps)) {
                    String[] variants = getDirVariants(left, right);

                    if (variants.length != 1) {
                        HashMap<Vector, String> newMap = new HashMap<>(tmp);
                        newMap.put(key, variants[1]);
                        dirMaps.add(newMap);
                    }
                    tmp.put(key, variants[0]);
                }
            }
        }
    }

    private String[] getDirVariants(Position left, Position right) {
        if (left.equals(right)) {
            return new String[]{"A"};
        }
        Position delta = right.copy().sub(left);
        if (left.y == 0 && right.x == 0)
            return new String[]{paths.get(new Position(0, delta.y)) + paths.get(new Position(delta.x, 0)) + "A"};
        if (left.x == 0 && right.y == 0)
            return new String[]{paths.get(new Position(delta.x, 0)) + paths.get(new Position(0, delta.y)) + "A"};
        if (delta.x == 0 || delta.y == 0) {
            return new String[]{paths.get(delta) + "A"};
        }
        return new String[]{
                paths.get(new Position(delta.x, 0)) + paths.get(new Position(0, delta.y)) + "A",
                paths.get(new Position(0, delta.y)) + paths.get(new Position(delta.x, 0)) + "A"
        };
    }

    private String[] getDigitVariants(Position left, Position right) {
        if (left.equals(right)) {
            return new String[]{"A"};
        }
        Position delta = right.copy().sub(left);
        if (left.y == 3 && right.x == 0)
            return new String[]{paths.get(new Position(0, delta.y)) + paths.get(new Position(delta.x, 0)) + "A"};
        if (left.x == 0 && right.y == 3)
            return new String[]{paths.get(new Position(delta.x, 0)) + paths.get(new Position(0, delta.y)) + "A"};
        if (delta.x == 0 || delta.y == 0) {
            return new String[]{paths.get(delta) + "A"};
        }
        return new String[]{
                paths.get(new Position(delta.x, 0)) + paths.get(new Position(0, delta.y)) + "A",
                paths.get(new Position(0, delta.y)) + paths.get(new Position(delta.x, 0)) + "A"
        };
    }
}
