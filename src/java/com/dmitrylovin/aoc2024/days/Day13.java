package com.dmitrylovin.aoc2024.days;

import java.util.Arrays;
import java.util.Iterator;

public class Day13 extends DayHandler {
    public Day13() {
        super("13");
        testValues = new Object[]{480L, 875318608908L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        return throughFile(input, 0L);
    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        return throughFile(input, 10000000000000L);
    }

    private long throughFile(String[] input, long add) {
        Iterator<String> iterator = Arrays.stream(input).iterator();
        long ax = 0, ay = 0, bx = 0, by = 0, x, y;
        long result = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.isEmpty()) {
                continue;
            }
            if (line.contains("Button A")) {
                String[] values = line.split("X\\+")[1].split(", ");
                ax = Integer.parseInt(values[0]);
                ay = Integer.parseInt(values[1].replace("Y+", ""));
            } else if (line.contains("Button B")) {
                String[] values = line.split("X\\+")[1].split(", ");
                bx = Integer.parseInt(values[0]);
                by = Integer.parseInt(values[1].replace("Y+", ""));
            } else if (line.contains("Prize")) {
                String[] values = line.split("X=")[1].split(", ");
                x = add + Integer.parseInt(values[0]);
                y = add + Integer.parseInt(values[1].replace("Y=", ""));

                double b = findB(x, y, ax, bx, ay, by);
                double a = (x - bx * b) / (double) ax;

                long rB = Math.round(b);
                long rA = Math.round(a);

                if(rB <= 0 || rA <= 0 || (ax * rA + bx * rB != x) || (ay * rA + by * rB != y))
                    continue;
                result += rA * 3 + rB;
            }
        }
        return result;
    }

    private double findB(long x, long y, long ax, long ay, long bx, long by) {
        return (y - (x * bx / (double) ax)) / (by - (bx * ay / (double) ax));
    }
}
