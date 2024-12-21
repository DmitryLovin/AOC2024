package com.dmitrylovin.aoc2024.models;

public class Vector {
    public int x;
    public int y;
    public int dx;
    public int dy;

    public Vector(Position pos) {
        this(pos.x, pos.y);
    }

    public Vector(Position pos, Position dir) {
        this(pos.x, pos.y, dir.x, dir.y);
    }

    public Vector(int x, int y) {
        this(x, y, 0, -1);
    }

    public Vector(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public Vector setPos(Position position) {
        this.x = position.x;
        this.y = position.y;
        return this;
    }

    public Vector rotate() {
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
        return this;
    }

    public Vector rotateBack() {
        if(dy == -1){
            dx = -1;
            dy = 0;
        } else if (dx == -1) {
            dx = 0;
            dy = 1;
        } else if (dy == 1) {
            dx = 1;
            dy = 0;
        } else if (dx == 1) {
            dx = 0;
            dy = -1;
        }
        return this;
    }

    public Vector move() {
        return move(1);
    }

    public Vector move(int times) {
        this.x += (this.dx * times);
        this.y += (this.dy * times);
        return this;
    }

    public Vector back() {
        this.x -= this.dx;
        this.y -= this.dy;
        return this;
    }

    public Position pos() {
        return new Position(x, y);
    }

    public boolean inLine(Position position) {
        if(position.x == x) {
            int diff = position.y - y;
            return (diff / Math.abs(diff) == dy);
        } else if(position.y == y) {
            int diff = position.x - x;
            return (diff / Math.abs(diff) == dx);
        }
        return false;
    }

    public Vector rotateTo(Position position) {
        if(position.x == x) {
            dx = 0;
            int diff = position.y - y;
            dy = diff / Math.abs(diff);
        } else if(position.y == y) {
            dy = 0;
            int diff = position.x - x;
            dx = diff / Math.abs(diff);
        }
        return this;
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
