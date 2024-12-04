package com.dmitrylovin.aoc2024.utils;

public class ArrayUtils {
    public static int[] remove(int[] arr, int in) {

        if (arr == null || in < 0 || in >= arr.length) {
            return arr;
        }

        int[] arr2 = new int[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == in)
                continue;

            arr2[k++] = arr[i];
        }

        return arr2;
    }

    public static String[][] splitChars(String[] lines) {
        String[][] result = new String[lines.length][lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].trim().split("");
            for (int j = 0; j < line.length; j++) {
                result[i][j] = line[j];
            }
        }
        return result;
    }

    public static String[] reversed(String[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    public static String[][] rotated(String[][] matrix) {
        String[][] result = new String[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[matrix.length - 1 - j][i];
            }
        }

        return result;
    }

    public static String[][] diagonal(String[][] matrix) {
        String[][] result = new String[matrix.length * 2 - 1][];

        for (int i = 0; i < result.length; i++) {
            result[i] = new String[i > result.length / 2 ? result.length - (i) : i + 1];
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

    public static String[][] diagonal2(String[][] matrix) {
        String[][] result = new String[matrix.length * 2 - 1][];

        for (int i = 0; i < result.length; i++) {
            result[i] = new String[i > result.length / 2 ? result.length - (i) : i + 1];
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
