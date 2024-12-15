package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;
import com.dmitrylovin.aoc2024.models.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Day12 extends DayHandler {
    HashMap<Position, String> map;
    HashMap<Position, String> groupped;
    HashMap<String, Integer> areas;
    HashMap<String, Integer> perimeters;
    HashMap<Vector, String> borders;
    int width;
    int height;

    final Position[] dirs = new Position[]{
            new Position(0, 1),
            new Position(0, -1),
            new Position(1, 0),
            new Position(-1, 0)
    };

    public Day12() {
        super("12");
        testValues = new Object[]{1930, 1206};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initCache(input);
        for (Map.Entry<Position, String> entry : groupped.entrySet()) {
            Position pos = entry.getKey();
            String key = entry.getValue();

            String up = groupped.get(pos.copy().add(0, -1));
            String right = groupped.get(pos.copy().add(1, 0));
            String bottom = groupped.get(pos.copy().add(0, 1));
            String left = groupped.get(pos.copy().add(-1, 0));
            if (!key.equals(up))
                perimeters.merge(key, 1, Integer::sum);
            if (!key.equals(right))
                perimeters.merge(key, 1, Integer::sum);
            if (!key.equals(bottom))
                perimeters.merge(key, 1, Integer::sum);
            if (!key.equals(left))
                perimeters.merge(key, 1, Integer::sum);
        }
        int result = 0;
        for (Map.Entry<String, Integer> entry : perimeters.entrySet()) {
            result += entry.getValue() * areas.get(entry.getKey());
        }
        return result;
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        initCache(input);
        for (Map.Entry<Position, String> entry : groupped.entrySet()) {
            Position pos = entry.getKey();
            String key = entry.getValue();

            String up = groupped.get(pos.copy().add(0, -1));
            String right = groupped.get(pos.copy().add(1, 0));
            String bottom = groupped.get(pos.copy().add(0, 1));
            String left = groupped.get(pos.copy().add(-1, 0));

            if (!key.equals(up))
                borders.put(new Vector(pos.x, pos.y, 0, -1), key);
            if (!key.equals(right))
                borders.put(new Vector(pos.x, pos.y, 1, 0), key);
            if (!key.equals(bottom))
                borders.put(new Vector(pos.x, pos.y, 0, 1), key);
            if (!key.equals(left))
                borders.put(new Vector(pos.x, pos.y, -1, 0), key);
        }
        HashSet<Vector> checked = new HashSet<>();
        for (Map.Entry<Vector, String> entry : borders.entrySet()) {
            if(checked.contains(entry.getKey()))
                continue;
            perimeters.merge(entry.getValue(), 1, Integer::sum);
            check(entry, checked);
        }
        int result = 0;
        for (Map.Entry<String, Integer> entry : perimeters.entrySet()) {
            result += entry.getValue() * areas.get(entry.getKey());
        }
        return result;
    }

    private void check(Map.Entry<Vector, String> entry, HashSet<Vector> checked) {
        Vector vec = entry.getKey();
        checked.add(entry.getKey());

        if(entry.getKey().dy != 0) {
            int i = 1;
            while (true){
                Vector newVec = new Vector(vec.x - i, vec.y, vec.dx, vec.dy);
                if(entry.getValue().equals(borders.get(newVec))) {
                    checked.add(newVec);
                    i++;
                } else {
                    break;
                }
            }
            i = 1;
            while (true){
                Vector newVec = new Vector(vec.x + i, vec.y, vec.dx, vec.dy);
                if(entry.getValue().equals(borders.get(newVec))) {
                    checked.add(newVec);
                    i++;
                } else {
                    break;
                }
            }
        } else {
            int i = 1;
            while (true){
                Vector newVec = new Vector(vec.x, vec.y - i, vec.dx, vec.dy);
                if(entry.getValue().equals(borders.get(newVec))) {
                    checked.add(newVec);
                    i++;
                } else {
                    break;
                }
            }
            i = 1;
            while (true){
                Vector newVec = new Vector(vec.x, vec.y + i, vec.dx, vec.dy);
                if(entry.getValue().equals(borders.get(newVec))) {
                    checked.add(newVec);
                    i++;
                } else {
                    break;
                }
            }
        }
    }

    private void initCache(String[] input) {
        this.map = new HashMap<>();
        this.groupped = new HashMap<>();
        this.areas = new HashMap<>();
        this.perimeters = new HashMap<>();
        this.borders = new HashMap<>();
        width = input[0].length();
        height = input.length;
        for (int y = 0; y < input.length; y++) {
            String[] gardens = input[y].split("");
            for (int x = 0; x < gardens.length; x++) {
                map.put(new Position(x, y), gardens[x]);
            }
        }
        HashMap<String, Integer> groupCount = new HashMap<>();
        for (Map.Entry<Position, String> entry : map.entrySet()) {
            if (groupped.containsKey(entry.getKey()))
                continue;
            String newKey = entry.getValue() + groupCount.getOrDefault(entry.getValue(), 0);
            groupCount.merge(entry.getValue(), 1, Integer::sum);

            findNext(entry.getKey(), entry.getValue(), newKey);
        }
    }

    private void findNext(Position pos, String key, String newKey) {
        groupped.put(pos, newKey);
        areas.merge(newKey, 1, Integer::sum);

        for (Position dir : dirs) {
            Position newPos = pos.copy().add(dir);
            if (newPos.inBorder(width, height) && !groupped.containsKey(newPos) && map.get(newPos).equals(key))
                findNext(newPos, key, newKey);
        }
    }
}
