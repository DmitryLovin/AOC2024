package com.dmitrylovin.aoc2024.days;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Day17 extends DayHandler {
    record Operand(Integer output, int pointer){}

    Long A_REG;
    Long B_REG;
    Long C_REG;

    HashMap<Integer, Supplier<Long>> comboValues = new HashMap<>() {
        {
            put(0, () -> 0L);
            put(1, () -> 1L);
            put(2, () -> 2L);
            put(3, () -> 3L);
            put(4, () -> A_REG);
            put(5, () -> B_REG);
            put(6, () -> C_REG);
            put(7, () -> -1L);
        }
    };

    HashMap<Integer, BiFunction<Integer, Integer, Operand>> operands = new HashMap<>() {{
        put(0, (value, pointer) -> {
            A_REG = (long) Math.floor((A_REG / (Math.pow(2, comboValues.get(value).get()))));
            return new Operand(null, pointer + 2);
        });
        put(1, (value, pointer) -> {
            B_REG = B_REG ^ value;
            return new Operand(null, pointer + 2);
        });
        put(2, (value, pointer) -> {
            B_REG = comboValues.get(value).get() % 8;
            return new Operand(null, pointer + 2);
        });
        put(3, (value,pointer) -> new Operand(null, A_REG == 0 ? pointer + 2 : value));
        put(4, (value, pointer) -> {
            B_REG = B_REG ^ C_REG;
            return new Operand(null, pointer + 2);
        });
        put(5, (value,pointer) -> new Operand((int) (comboValues.get(value).get() % 8), pointer + 2));
        put(6, (value, pointer) -> {
            B_REG = (long) Math.floor((A_REG / (Math.pow(2, comboValues.get(value).get()))));
            return new Operand(null, pointer + 2);
        });
        put(7, (value, pointer) -> {
            C_REG = (long) Math.floor((A_REG / (Math.pow(2, comboValues.get(value).get()))));
            return new Operand(null, pointer + 2);
        });
    }};

    int[] program;

    public Day17() {
        super("17");
        testValues = new Object[]{"5,7,3,0", 117440L};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        A_REG = Long.parseLong(iterator.next().replace("Register A: ", ""));
        B_REG = Long.parseLong(iterator.next().replace("Register B: ", ""));
        C_REG = Long.parseLong(iterator.next().replace("Register C: ", ""));
        iterator.next();
        program = Arrays.stream(iterator.next().replace("Program: ","").split(",")).mapToInt(Integer::parseInt).toArray();
        List<String> output = new ArrayList<>();
        throughProgram(new Operand(null, 0), output);
        return String.join(",", output);

    }

    @Override
    Object partTwo(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        Iterator<String> iterator = Arrays.stream(input).iterator();
        A_REG = Long.parseLong(iterator.next().replace("Register A: ", ""));
        B_REG = Long.parseLong(iterator.next().replace("Register B: ", ""));
        C_REG = Long.parseLong(iterator.next().replace("Register C: ", ""));
        long originalAReg = A_REG;
        long originalBReg = B_REG;
        long originalCReg = C_REG;

        iterator.next();
        String programString = iterator.next().replace("Program: ","");

        program = Arrays.stream(programString.split(",")).mapToInt(Integer::parseInt).toArray();

        long aReg = 0L;

        for(int i =0;i<program.length;i++){
            List<String> output = new ArrayList<>();
            for(int j = program.length - 1 - i; j <= program.length - 1; j++){
                output.add(String.valueOf(program[j]));
            }
            while(true) {
                if(aReg == originalAReg) {
                    aReg++;
                    continue;
                }
                B_REG = originalBReg;
                C_REG = originalCReg;
                A_REG = aReg;
                List<String> tmp = new ArrayList<>();
                boolean result = sameProgram(new Operand(null, 0), tmp, output);
                if(result) {
                    break;
                }
                aReg++;
            }
            aReg *= 8;
        }

        return aReg / 8;
    }

    private void throughProgram(Operand operand, List<String> output) {
        int op = program[operand.pointer];
        int combo = program[operand.pointer + 1];
        Operand nextOperand = operands.get(op).apply(combo, operand.pointer);
        if(nextOperand.output != null) {
            output.add(String.valueOf(nextOperand.output));
        }
        if(nextOperand.pointer < program.length)
            throughProgram(nextOperand, output);
    }

    private boolean sameProgram(Operand operand, List<String> output, List<String> original) {
        int op = program[operand.pointer];
        int combo = program[operand.pointer + 1];
        Operand nextOperand = operands.get(op).apply(combo, operand.pointer);
        if(nextOperand.output != null) {
            if(original.size() == output.size()) {
                return false;
            }
            if(original.get(output.size()).equals(String.valueOf(nextOperand.output))) {
                output.add(String.valueOf(nextOperand.output));
            } else {
                return false;
            }
        }
        if(nextOperand.pointer < program.length)
            return sameProgram(nextOperand, output, original);
        else
            return String.join(",",output).equals(String.join(",",original));
    }
}
