package com.dmitrylovin.aoc2024;

import com.dmitrylovin.aoc2024.days.Day01;
import com.dmitrylovin.aoc2024.days.DayHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Main {
    private static final Map<Integer, Supplier<DayHandler>> DAYS;

    static {
        DAYS = new HashMap<>();

        DAYS.put(1, Day01::new);
    }

    private static final BufferedReader READER = new BufferedReader(
            new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int day = pickValue("day");
        if (DAYS.containsKey(day))
            DAYS.get(day).get().handle();
    }

    private static int pickValue(String type) throws IOException {
        System.out.printf("Pick a %s: %n", type);
        return Integer.parseInt(READER.readLine());
    }
}