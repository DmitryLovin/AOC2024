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
}
