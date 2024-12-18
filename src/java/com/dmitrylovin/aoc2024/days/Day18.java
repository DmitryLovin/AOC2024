package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;

import java.util.*;

public class Day18 extends DayHandler {
    HashSet<Position> walls;
    HashMap<Position, Integer> cache;

    final Position[] directions = new Position[]{
            new Position(0, 1),
            new Position(1, 0),
            new Position(0, -1),
            new Position(-1, 0)
    };

    int width;
    int height;

    public Day18() {
        super("18");
        testValues = new Object[]{22, "6,1"};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;

        width = isTestRun ? 7 : 71;
        height = width;
        initMap(Arrays.stream(input).iterator(), isTestRun ? 12 : 1024);

        return findShortestPath(new Position(0,0));

    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        width = isTestRun ? 7 : 71;
        height = width;

        walls = new HashSet<>();
        Position finish = new Position(width - 1, height - 1);
        HashSet<Position> group = new HashSet<>();
        Queue<Position> queue = new ArrayDeque<>();

        Iterator<String> iterator = Arrays.stream(input).iterator();

        int index = 0;

        while(iterator.hasNext()) {
            String coords = iterator.next();
            if(!groupWithFinish(coords.split(","), index, isTestRun ? 12 : 1024, group, queue, finish))
                return coords;
            index++;
        }
        return 0;
    }

    private void initMap(Iterator<String> iterator, int steps) {
        walls = new HashSet<>();
        cache = new HashMap<>();

        int i = 0;
        while(i < steps) {
            String[] coords = iterator.next().split(",");
            walls.add(new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            i++;
        }
    }

    private int findShortestPath(Position position) {
        Queue<Object[]> queue = new ArrayDeque<>();
        queue.add(new Object[]{position, new HashSet<>()});

        int result = Integer.MAX_VALUE;

        while(!queue.isEmpty()){
            Object[] item = queue.remove();
            Position currentPosition = (Position) item[0];
            HashSet<Position> path = (HashSet<Position>) item[1];

            if(currentPosition.x == width - 1 && currentPosition.y == height - 1) {
                result = Math.min(result, path.size());
            }

            path.add(currentPosition);

            if(cache.getOrDefault(currentPosition, Integer.MAX_VALUE) <= path.size())
                continue;

            cache.put(currentPosition, path.size());

            for(Position dir: directions){
                Position newPos = currentPosition.copy().add(dir);
                if(walls.contains(newPos) || path.contains(newPos) || !newPos.inBorder(width, height))
                    continue;
                queue.add(new Object[]{newPos, new HashSet<>(path)});
            }
        }

        return result;
    }

    private boolean groupWithFinish(String[] coords, int index, int start, HashSet<Position> group, Queue<Position> queue, Position finish) {
        Position wallPosition = new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        walls.add(wallPosition);
        if(index < start)
            return true;

        if(!group.isEmpty() && !group.contains(wallPosition))
            return true;

        resetQueue(group, queue);

        while (!queue.isEmpty()){
            Position currentPosition = queue.remove();
            for(Position direction: directions) {
                checkPosition(currentPosition.copy().add(direction), group, queue);
            }
        }

        return group.contains(finish);
    }

    private void resetQueue(HashSet<Position> group, Queue<Position> queue) {
        queue.add(new Position(0, 0));
        group.clear();
        group.add(new Position(0, 0));
    }

    private void checkPosition(Position position, HashSet<Position> group, Queue<Position> queue) {
        if(walls.contains(position) || group.contains(position) || !position.inBorder(width, height))
            return;
        group.add(position);
        queue.add(position);
    }
}
