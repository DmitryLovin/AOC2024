package com.dmitrylovin.aoc2024.utils;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ArrayUtils {
    public static void moveDown (int[] arr, int from, int to) {
        int value = arr[from];
        for(int i = from; i > to; i--){
            arr[i] = arr[i - 1];
        }
        arr[to] = value;
    }

    public static int[] insert(int[] arr, int value, int index) {
        int[] result = new int[arr.length + 1];

        for (int i = 0; i < result.length; i++) {
            if (i < index)
                result[i] = arr[i];
            else if (i == index)
                result[i] = value;
            else
                result[i] = arr[i - 1];
        }

        return result;
    }

    public static int[] remove(int[] arr, int index) {
        int[] result = new int[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i != index)
                result[k++] = arr[i];
        }

        return result;
    }

    public static String[][] matrix(String[] lines) {
        String[][] result = new String[lines.length][lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split("");
            for (int j = 0; j < line.length; j++) {
                result[i][j] = line[j];
            }
        }
        return result;
    }

    public static <T> T[] mapString(Class<?> klazz, String[] array, Function<String, T> supplier) {
        T[]result = (T[]) Array.newInstance(klazz, array.length);
        for(int i = 0; i<result.length;i++){
            result[i] = supplier.apply(array[i]);
        }
        return result;
    }

    public static <T> T[][] mappedMatrix(String[] lines, Map<String, T> map) {
        Class<?> klazz = map.values().iterator().next().getClass();
        T[][] result = (T[][]) Array.newInstance(klazz, lines.length, lines.length);

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split("");
            for (int j = 0; j < line.length; j++) {
                result[i][j] = map.get(line[j]);
            }
        }
        return result;
    }

    public static <T> T[] reversed(T[] array) {
        Class<?> klazz = array[0].getClass();
        T[] result = (T[]) Array.newInstance(klazz, array.length);
        for (int i = 0; i < array.length; i++) {
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    public static <T> T[][] rotated(T[][] matrix) {
        Class<?> klazz = matrix[0][0].getClass();
        T[][] result = (T[][]) Array.newInstance(klazz, matrix.length, matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[matrix.length - 1 - j][i];
            }
        }

        return result;
    }

    public static <T> T[][] diagonal(T[][] matrix) {
        Class<?> klazz = matrix[0][0].getClass();
        T[][] result = (T[][]) Array.newInstance(klazz, matrix.length * 2 - 1, 0);

        for (int i = 0; i < result.length; i++) {
            result[i] = (T[]) Array.newInstance(klazz, i > result.length / 2 ? result.length - (i) : i + 1);
            for (int j = 0; j < result[i].length; j++) {
                if (i + 1 > matrix.length) {
                    result[i][j] = matrix[i - j - (i - (matrix.length - 1))][j + (i - (matrix.length - 1))];
                } else {
                    result[i][j] = matrix[i - j][j];
                }
            }
        }

        return result;
    }
}
