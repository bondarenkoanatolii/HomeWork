package com.hillel.anatoliibondarenko.homework9;

import java.util.Arrays;

public class ValueCalculator {
    private final float[] array;
    private final int size;
    private final int halfSize;

    public ValueCalculator(int size) {
        this.size = size;
        array = new float[size];
        halfSize = size / 2;
    }

    static class Part implements Runnable {
        private final float[] partOfArray;
        private final int offset;
        private final int quantity;
        
        public Part(float[] partOfArray, int offset, int quantity) {
            this.offset = offset;
            this.quantity = quantity;
            this.partOfArray = partOfArray;
        }

        @Override
        public void run() {
            int indexInInitialArray;
            for (int i = 0; i < quantity; i++) {
                indexInInitialArray = offset + i;
                partOfArray[i] = (float) (partOfArray[i] * Math.sin(0.2f + (float) indexInInitialArray / 5)
                        * Math.cos(0.2f + (float) indexInInitialArray / 5)
                        * Math.cos(0.4f + (float) indexInInitialArray / 2));
            }
        }
    }


    void doCalc() throws InterruptedException {
        //First, let's do everything in one flow and measure the time.
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }

        for (int i = 0; i < size; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (float) i / 5)
                    * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
        }

        long end = System.currentTimeMillis();
        System.out.printf("Time work array of %,d elements in one thread - %d ms.\n", size, end - start);

        //Let's try to do this in two threads to save time. Everything should be the same.
        // The arrays at the end must be the same.
        long startThreads = System.currentTimeMillis();
        float[] arrayThreads = new float[size];

        for (int i = 0; i < size; i++) {
            arrayThreads[i] = 1;
        }

        float[] firstHalf = new float[halfSize];
        float[] secondHalf = new float[size - halfSize];

        System.arraycopy(arrayThreads, 0, firstHalf, 0, halfSize);
        System.arraycopy(arrayThreads, halfSize, secondHalf, 0, size - halfSize);

        Thread part1 = new Thread(new Part(firstHalf, 0 , halfSize));
        Thread part2 = new Thread(new Part(secondHalf, halfSize , size - halfSize));
        part1.start();
        part2.start();
        part1.join();
        part2.join();

        System.arraycopy(firstHalf, 0, arrayThreads, 0, halfSize);
        System.arraycopy(secondHalf, 0, arrayThreads, halfSize, size - halfSize);

        long endThreads = System.currentTimeMillis();
        System.out.printf("Time work array of %,d elements in two threads - %d ms.\n", size, endThreads - startThreads);


        if (Arrays.equals(array, arrayThreads)) {
            System.out.println("Test Ok. Arrays have the same values.");
        } else {
            System.out.println("Test Failed. Check program.");
        }
    }



}
