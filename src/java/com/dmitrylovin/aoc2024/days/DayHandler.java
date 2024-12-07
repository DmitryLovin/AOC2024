package com.dmitrylovin.aoc2024.days;

import java.util.function.Supplier;

public abstract class DayHandler {
    public void handle() {
        System.out.printf("P1 Test: %s\n", partOne(true));
        System.out.printf("P1: \n\t%s\n",bench(() -> partOne(false)));
        System.out.printf("P2 Test: %s\n", partTwo(true));
        System.out.printf("P2: \n\t%s\n",bench(() -> partTwo(false)));
    }

    abstract Object partOne(boolean isTestRun);

    abstract Object partTwo(boolean isTestRun);

    private String bench(Supplier<Object> action) {
        Object result = action.get();
        long current = System.nanoTime();
        for(int i = 0; i < 10; i++){
            result = action.get();
        }
        double time = (System.nanoTime() - current) / 10000000.0d;
        return String.format("Result: %s\n\tAvg of 10: %.3fms", result, time);
    }
}
