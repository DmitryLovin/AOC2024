package com.dmitrylovin.aoc2024.days;

public abstract class DayHandler {
    public void handle() {
        System.out.printf("P1 Test: %s\n", partOne(true));
        System.out.printf("P1: %s\n", partOne(false));
        System.out.printf("P2 Test: %s\n", partTwo(true));
        System.out.printf("P2: %s\n", partTwo(false));
    }

    abstract Object partOne(boolean isTestRun);
    abstract Object partTwo(boolean isTestRun);
}
