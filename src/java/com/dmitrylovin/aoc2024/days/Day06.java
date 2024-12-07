package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Position;
import com.dmitrylovin.aoc2024.models.Vector;
import com.dmitrylovin.aoc2024.utils.FileUtils;

import java.util.HashSet;
import java.util.Set;

public class Day06 extends DayHandler {
    private final String[] input;
    private final String[] testInput;
    Set<Position> OBSTACLES;

    public Day06() {
        input = FileUtils.parseInput("06");
        testInput = FileUtils.parseTestInput("06");
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Vector init = init(input);
        int height = input.length;
        int width = input[0].length();

        return visited(init, width, height).size();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Vector init = init(input);
        int height = input.length;
        int width = input[0].length();

        HashSet<Position> possibleObstacles = visited(init.copy(), width, height);
        possibleObstacles.remove(init.pos());

        return possibleObstacles.stream().parallel().mapToInt((pos) -> deadEnd(init, pos, width, height)).sum();
    }

    private Vector init(String[] input) {
        OBSTACLES = new HashSet<>();
        Vector init = new Vector(0, 0);

        for (int y = 0; y < input.length; y++) {
            String[] row = input[y].split("");
            for (int x = 0; x < row.length; x++) {
                if (row[x].equals("#")) {
                    OBSTACLES.add(new Position(x, y));
                } else if (row[x].equals("^")) {
                    init = new Vector(x, y);
                }
            }
        }
        return init;
    }

    private HashSet<Position> visited(Vector pos, int width, int height) {
        HashSet<Position> visited = new HashSet<>();
        while (pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height){
            visited.add(pos.pos());
            if(OBSTACLES.contains(pos.copy().move().pos())) {
                pos.rotate();
            } else {
                pos.move();
            }
        }
        return visited;
    }

    private int deadEnd (Vector init, Position obstacle, int width, int height) {
        HashSet<Vector> visit = new HashSet<>();
        Vector pos = init.copy();
        while (pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height){
            if(visit.contains(pos)){
                return 1;
            }
            visit.add(pos.copy());

            Position check = pos.copy().move().pos();
            if(OBSTACLES.contains(check) || obstacle.equals(check)) {
                pos.rotate();
            } else {
                pos.move();
            }
        }
        return 0;
    }
}
