package com.dmitrylovin.aoc2024.models;

public class Vector {
    public int x;
    public int y;
    public int dx;
    public int dy;

    public Vector(int x, int y) {
        this(x, y, 0, -1);
    }

    public Vector(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void rotate() {
        if(dy == -1){
            dx = 1;
            dy = 0;
        } else if (dx == 1) {
            dx = 0;
            dy = 1;
        } else if (dy == 1) {
            dx = -1;
            dy = 0;
        } else if (dx == -1) {
            dx = 0;
            dy = -1;
        }
    }

    public Vector move() {
        return move(1);
    }

    public Vector move(int times) {
        this.x += (this.dx * times);
        this.y += (this.dy * times);
        return this;
    }

    public Position pos() {
        return new Position(x, y);
    }

    public Position dir() {
        return new Position(dx, dy);
    }

    public Vector copy() {
        return new Vector(x, y, dx, dy);
    }

    @Override
    public int hashCode() {

        return dy * 1000000 + dx * 100000 + y * 1000 + x;
    }

    @Override
    public boolean equals(Object another) {
        return this.x == ((Vector)another).x && this.y == ((Vector)another).y &&
                this.dx == ((Vector)another).dx && this.dy == ((Vector)another).dy;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d (%d, %d))", x , y, dx, dy);
    }
}
