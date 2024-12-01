package com.dmitrylovin.aoc2024.utils;

import com.dmitrylovin.aoc2024.Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    static final String INPUT_PATH = "input_data/";

    static final ClassLoader loader = Main.class.getClassLoader();

    public static String[] parseTestInput(String day) {
        return parseInput(String.format("examples/%s", day));
    }

    public static String[] parseInput(String suffix) {
        InputStream inputStream = loader.getResourceAsStream(String.format("%s%s.txt", INPUT_PATH, suffix));

        if (inputStream == null) {
            return new String[0];
        }

        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        return reader.lines().toArray(String[]::new);
    }
}
