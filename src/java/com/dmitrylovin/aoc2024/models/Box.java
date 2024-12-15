package com.dmitrylovin.aoc2024.models;

public class Box extends Position {
    public int sizeX;
    public int sizeY;

    public Box(int x, int y) {
        this(x, y, 1, 1);
    }

    public Box(int x, int y, int sizeX, int sizeY) {
        super(x, y);
        this.sizeX = sizeX - 1;
        this.sizeY = sizeY - 1;
    }

    public boolean inBorder(int width, int height) {
        return x >= 0 && y >= 0 && x + sizeX < width && y + sizeY < height;
    }

    public Box copy() {
        return new Box(x, y, sizeX + 1, sizeY + 1);
    }

    public Box add(Position delta) {
        this.x += delta.x;
        this.y += delta.y;
        return this;
    }

    public Box sub(Position delta) {
        this.x -= delta.x;
        this.y -= delta.y;
        return this;
    }

    public Position[] positions() {
        Position[] result = new Position[(sizeX + 1) * (sizeY + 1)];
        int index = 0;
        for (int x = 0; x <= sizeX; x++) {
            for (int y = 0; y <= sizeY; y++) {
                result[index] = new Position(this.x + x, this.y + y);
                index++;
            }
        }
        return result;
    }
}
