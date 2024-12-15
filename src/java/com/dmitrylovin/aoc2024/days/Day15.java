package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Box;
import com.dmitrylovin.aoc2024.models.Position;

import java.util.List;
import java.util.*;

public class Day15 extends DayHandler {
    List<Box> boxes;
    Set<Box> borders;
    Box robot;
    HashMap<String, Position> directions = new HashMap<>() {{
        put("^", new Position(0, -1));
        put(">", new Position(1, 0));
        put("<", new Position(-1, 0));
        put("v", new Position(0, 1));
    }};
    int width;
    int height;


    public Day15() {
        super("15");
        testValues = new Object[]{10092L, 9021L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        Box robot = initMap(iterator);
        while (iterator.hasNext()) {
            for (String move : iterator.next().split("")) {
                tryMove(robot, directions.get(move));
            }
        }
        long result = 0L;
        for (Position box : boxes) {
            result += (box.y + 1) * 100 + box.x + 1;
        }
        return result;
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        robot = initMapV2(iterator);

        while (iterator.hasNext()) {
            for (String move : iterator.next().split("")) {
                HashSet<Position> moved = new HashSet<>();
                Position dir = directions.get(move);
                if (!tryMoveV2(robot, dir, moved)) {
                    for (Position pos : moved) {
                        pos.sub(dir);
                    }
                }
            }
        }

        long result = 0L;

        for (Position box : boxes) {
            result += (box.y + 1) * 100 + box.x + 2;
        }

        return result;
    }

    private boolean tryMove(Box pos, Position dir) {
        Box nextPos = pos.copy().add(dir);
        if (!nextPos.inBorder(width, height) || borders.contains(nextPos))
            return false;
        int index = boxes.indexOf(nextPos);
        if (index >= 0) {
            if (tryMove(boxes.get(index), dir)) {
                pos.add(dir);
                return true;
            } else {
                return false;
            }
        }
        {
            pos.add(dir);
            return true;
        }
    }

    private boolean tryMoveV2(Box pos, Position dir, HashSet<Position> moved) {
        Box nextPos = pos.copy().add(dir);
        if (!nextPos.inBorder(width, height)) {
            return false;
        }
        Position[] positions = nextPos.positions();

        if (Arrays.stream(positions).anyMatch((p) -> borders.contains(p))) {
            return false;
        }

        boolean canMoveAll = true;

        for (Position position : positions) {
            int index = Math.max(boxes.indexOf(position), boxes.indexOf(position.copy().add(-1, 0)));
            if (index >= 0) {
                if (boxes.get(index).equals(pos)) {
                    continue;
                }

                if (tryMoveV2(boxes.get(index), dir, moved)) {
                    moved.add(boxes.get(index));
                } else {
                    canMoveAll = false;
                    break;
                }
            }
        }

        if (canMoveAll) {
            pos.add(dir);
            return true;
        } else {
            return false;
        }
    }

    private Box initMap(Iterator<String> iterator) {
        boxes = new ArrayList<>();
        borders = new HashSet<>();
        Box robot = new Box(0, 0);
        int y = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.equals(""))
                break;
            String[] items = line.split("");
            width = items.length - 2;
            for (int x = 0; x < items.length; x++) {
                if (items[x].equals("O"))
                    boxes.add(new Box(x - 1, y - 1));
                else if (items[x].equals("#"))
                    borders.add(new Box(x - 1, y - 1));
                else if (items[x].equals("@"))
                    robot = new Box(x - 1, y - 1);
            }
            y++;
        }
        height = y - 2;
        return robot;
    }

    private Box initMapV2(Iterator<String> iterator) {
        boxes = new ArrayList<>();
        borders = new HashSet<>();
        Box robot = new Box(0, 0);
        int y = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.equals(""))
                break;
            String[] items = line.split("");
            width = (items.length - 2) * 2;
            for (int x = 0; x < items.length; x++) {
                if (items[x].equals("O"))
                    boxes.add(new Box((x - 1) * 2, (y - 1), 2, 1));
                else if (items[x].equals("#")) {
                    borders.add(new Box((x - 1) * 2, (y - 1)));
                    borders.add(new Box((x - 1) * 2 + 1, (y - 1)));
                } else if (items[x].equals("@"))
                    robot = new Box((x - 1) * 2, (y - 1));
            }
            y++;
        }
        height = (y - 2);
        return robot;
    }
}
