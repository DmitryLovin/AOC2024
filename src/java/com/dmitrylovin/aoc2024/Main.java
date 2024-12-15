package com.dmitrylovin.aoc2024;

import com.dmitrylovin.aoc2024.days.*;

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
        DAYS.put(2, Day02::new);
        DAYS.put(3, Day03::new);
        DAYS.put(4, Day04::new);
        DAYS.put(5, Day05::new);
        DAYS.put(6, Day06::new);
        DAYS.put(7, Day07::new);
        DAYS.put(8, Day08::new);
        DAYS.put(9, Day09::new);
        DAYS.put(10, Day10::new);
        DAYS.put(11, Day11::new);
        DAYS.put(12, Day12::new);
        DAYS.put(13, Day13::new);
        DAYS.put(14, Day14::new);
        DAYS.put(15, Day15::new);
    }

    private static final BufferedReader READER = new BufferedReader(
            new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        if (args.length == 1 && args[0].equals("--all")) {
            for (Map.Entry<Integer, Supplier<DayHandler>> supplierEntry : DAYS.entrySet()) {
                System.out.printf("DAY %d:\n", supplierEntry.getKey());
                supplierEntry.getValue().get().handle();
            }
        } else {
            int day = pickValue("day");
            if (DAYS.containsKey(day))
                DAYS.get(day).get().handle();
        }
    }

    private static int pickValue(String type) throws IOException {
        System.out.printf("Pick a %s: %n", type);
        return Integer.parseInt(READER.readLine());
    }
}