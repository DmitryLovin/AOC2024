package com.dmitrylovin.aoc2024.models;

public record IntPair(int left, int right) {

    @Override
    public int hashCode() {
        return left * 1000 + right;
    }

    public IntPair copy() {
        return new IntPair(left, right);
    }
}
