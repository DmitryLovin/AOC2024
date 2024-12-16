package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;
import com.dmitrylovin.aoc2024.models.Vector;

import java.util.*;

public class Day16 extends DayHandler {
    Set<Position> map;
    Vector player;
    Position finish;
    HashMap<Position, HashSet<Position>> lines;

    public Day16() {
        super("16");
        testValues = new Object[]{11048, 64};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        return findPath(player, new HashMap<>(), 0);
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        initMap(iterator);
        HashSet<List<Position>> paths = new HashSet<>();
        findAllPaths(player, new HashMap<>(), new ArrayList<>(), paths, 0, findPath(player.copy(), new HashMap<>(), 0));
        HashSet<Position> seats = new HashSet<>();
        for (List<Position> path : paths) {
            for(int i = 0; i <path.size() - 1;i++) {
                Position end = path.get(i + 1);
                Vector start = new Vector(path.get(i)).rotateTo(end);

                while(!start.pos().equals(end)) {
                    seats.add(start.pos());
                    start.move();
                }
            }
        }
        return seats.size() + 1;
    }

    private int findPath(Vector vec, HashMap<Position, Integer> weights, int currentScore) {
        if (vec.pos().equals(finish))
            return currentScore;
        if (weights.getOrDefault(vec.pos(), Integer.MAX_VALUE) <= currentScore)
            return Integer.MAX_VALUE;

        weights.put(vec.pos(), currentScore);

        int result = Integer.MAX_VALUE;
        for(Position position:lines.get(vec.pos())) {
            Vector newVec = vec.copy();
            int score = Math.abs((position.x - newVec.x) + (position.y - newVec.y));
            if(!newVec.inLine(position)) {
                score += 1000;
                newVec.rotateTo(position);
            }

            result = Math.min(findPath(newVec.setPos(position), weights, currentScore + score), result);
        }
        return result;
    }

    private int findAllPaths(Vector vec, HashMap<Position, Integer> weights, List<Position> currentPath, HashSet<List<Position>> paths, int currentScore, int maxValue) {
        if (vec.pos().equals(finish)) {
            if(currentScore == maxValue) {
                currentPath.add(vec.pos());
                paths.add(currentPath);
            }
            return currentScore;
        }
        if (weights.getOrDefault(vec.pos(), Integer.MAX_VALUE) < currentScore - 1000)
            return Integer.MAX_VALUE;

        weights.put(vec.pos(), currentScore);
        currentPath.add(vec.pos());

        if (currentScore >= maxValue)
            return Integer.MAX_VALUE;

        int result = Integer.MAX_VALUE;
        for(Position position:lines.get(vec.pos())) {
            Vector newVec = vec.copy();
            int score = Math.abs((position.x - newVec.x) + (position.y - newVec.y));
            if(!newVec.inLine(position)) {
                score += 1000;
                newVec.rotateTo(position);
            }

            result = Math.min(findAllPaths(newVec.setPos(position), weights, new ArrayList<>(currentPath), paths, currentScore + score, maxValue), result);
        }
        return result;
    }

    private void initMap(Iterator<String> iterator) {
        map = new HashSet<>();
        int y = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] items = line.split("");
            for (int x = 0; x < items.length; x++) {
                if (items[x].equals("."))
                    map.add(new Position(x, y));
                else if (items[x].equals("E")) {
                    map.add(new Position(x, y));
                    finish = new Position(x, y);
                } else if (items[x].equals("S")) {
                    map.add(new Position(x, y));
                    player = new Vector(x, y, 1, 0);
                }
            }
            y++;
        }
        lines = new HashMap<>();
        for (Position space : map) {
            if (isCorner(space)) {
                HashSet<Position> positions = new HashSet<>();

                Position right = toTheWall(new Vector(space.x, space.y, 1, 0).move());
                Position left = toTheWall(new Vector(space.x, space.y, -1, 0).move());
                Position up = toTheWall(new Vector(space.x, space.y, 0, -1).move());
                Position down = toTheWall(new Vector(space.x, space.y, 0, 1).move());

                if (!space.equals(right))
                    positions.add(right);
                if (!space.equals(left))
                    positions.add(left);
                if (!space.equals(up))
                    positions.add(up);
                if (!space.equals(down))
                    positions.add(down);

                lines.put(space, positions);
            }
        }
    }

    private boolean isCorner(Position space) {
        return !((map.contains(space.copy().add(0, 1))
                && map.contains(space.copy().add(0, -1))
                && !map.contains(space.copy().add(1, 0))
                && !map.contains(space.copy().add(-1, 0)))
                ||
                (map.contains(space.copy().add(1, 0))
                        && map.contains(space.copy().add(-1, 0))
                        && !map.contains(space.copy().add(0, 1))
                        && !map.contains(space.copy().add(0, -1)))
        );
    }

    private Position toTheWall(Vector pos) {
        while (map.contains(pos.pos())) {
            pos.move();
            if (isCorner(pos.pos()))
                return pos.pos();
        }
        return pos.back().pos();
    }
}
