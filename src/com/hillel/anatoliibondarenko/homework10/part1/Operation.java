package com.hillel.anatoliibondarenko.homework10.part1;

import java.util.Random;

/**
 *  Operations that will be performed on ThreadSafeList
 */
public enum Operation {
    ADD, REMOVE, GET;

    private static final Random RANDOM = new Random();

    /**
     * Randomly choose the type of operation
     * @return the type of operation
     */
    public static Operation randomOperation()  {
        Operation[] operations = values();
        return operations[RANDOM.nextInt(operations.length)];
    }
}
