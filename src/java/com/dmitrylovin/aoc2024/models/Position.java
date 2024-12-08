package com.dmitrylovin.aoc2024.models;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotate() {
        if (y == -1) {
            x = 1;
            y = 0;
        } else if (x == 1) {
            x = 0;
            y = 1;
        } else if (y == 1) {
            x = -1;
            y = 0;
        } else if (x == -1) {
            x = 0;
            y = -1;
        }
    }

    public Position normalize() {
        if (x == 0) {
            y /= Math.abs(y);
            return this;
        } else if (y == 0) {
            x /= Math.abs(x);
            return this;
        }
        int i = 2;
        while (i <= Math.abs(x) && i <= Math.abs(y)) {
            if (x % i == 0 && y % i == 0) {
                x /= i;
                y /= i;
            } else {
                i++;
            }
        }
        return this;
    }

    public boolean inBorder(int width, int height) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Position add(Position delta) {
        this.x += delta.x;
        this.y += delta.y;
        return this;
    }

    public Position sub(Position delta) {
        this.x -= delta.x;
        this.y -= delta.y;
        return this;
    }

    public Position copy() {
        return new Position(x, y);
    }

    @Override
    public int hashCode() {
        return y * 1000 + x;
    }

    @Override
    public boolean equals(Object another) {
        return this.x == ((Position) another).x && this.y == ((Position) another).y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
