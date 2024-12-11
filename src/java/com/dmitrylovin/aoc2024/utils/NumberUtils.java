package com.dmitrylovin.aoc2024.utils;

public class NumberUtils {

    // according to benchmarks, it is the dirtiest but the fastest way to get multiplication value to merge integers
    public static int power(int value) {
        if (value < 10)
            return 10;
        if (value < 100)
            return 100;
        if (value < 1000)
            return 1000;
        if (value < 10000)
            return 10000;
        if (value < 100000)
            return 100000;
        if (value < 1000000)
            return 1000000;
        if (value < 10000000)
            return 10000000;
        if (value < 100000000)
            return 100000000;
        return 1000000000;
    }

    public static int lengthLong(long value) {
        if (value < 10000000000L) {
            if (value < 100000) {
                if (value < 10)
                    return 1;
                if (value < 100)
                    return 2;
                if (value < 1000)
                    return 3;
                if (value < 10000)
                    return 4;
                return 5;
            } else {
                if (value < 1000000)
                    return 6;
                if (value < 10000000L)
                    return 7;
                if (value < 100000000L)
                    return 8;
                if (value < 1000000000L)
                    return 9;
                return 10;
            }
        } else {
            if (value < 100000000000000L) {
                if (value < 100000000000L)
                    return 11;
                if (value < 1000000000000L)
                    return 12;
                if (value < 10000000000000L)
                    return 13;
                return 14;
            } else {
                if (value < 1000000000000000L)
                    return 15;
                if (value < 10000000000000000L)
                    return 16;
                if (value < 100000000000000000L)
                    return 17;
                if (value < 1000000000000000000L)
                    return 18;
                return 19;
            }
        }
    }
}
