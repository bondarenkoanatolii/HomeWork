package com.hillel.anatoliibondarenko.homework9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ValueCalculatorExtend {
    private final int size;
    private final int maxCountOfThreads;
    private final float[] array;
    private final float[] arrayOfParts;

    public ValueCalculatorExtend(int size, int maxCountOfThreads) {
        this.size = size;
        this.maxCountOfThreads = maxCountOfThreads;
        this.array = new float[size];
        this.arrayOfParts = new float[size];
    }

    /**
     *  Клас проводить обчислення значень масииву arrayOfParts
     *  в межаж від startPos до (startPos + quantity)
     */
        class Part implements Runnable {
        private final int startPos;
        private final int endPos;

        public Part(int startPos, int quantity) {
            this.startPos = startPos;
            this.endPos = startPos + quantity;
        }

        @Override
        public void run() {
            for (int i = startPos; i < endPos; i++) {
                arrayOfParts[i] = (float) (arrayOfParts[i] * Math.sin(0.2f + (float) i / 5)
                        * Math.cos(0.2f + (float) i / 5)
                        * Math.cos(0.4f + (float) i / 2));
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

        // Спробуємо зробити те ж саме з різними кількостями потоків.
        // Масив фізично не будемо розбивати на частини. Всі потоки роблять з одним масивом.
        // У кожного потоку своя частина масиву для обчислень.
        for (int numberOfThreads = 2; numberOfThreads <= maxCountOfThreads; numberOfThreads++) {

            start = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                arrayOfParts[i] = 1;
            }

            int sizeSubArray = size / numberOfThreads;
            ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < numberOfThreads; i++) {
                if (i != numberOfThreads - 1) {
                    futures.add(exec.submit(new Part(i * sizeSubArray, sizeSubArray)));
                } else {
                    futures.add(exec.submit(new Part(i * sizeSubArray, size - i * sizeSubArray)));
                }
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    exec.shutdown();
                }
            }

            end = System.currentTimeMillis();
            System.out.printf("Time work array of %,d elements in %d threads - %d ms. ", size, numberOfThreads, end - start);

            if (Arrays.equals(array, arrayOfParts)) {
                System.out.println("Test Ok. Arrays have the same values.");
            } else {
                System.out.println("Test Failed. Check program.");
            }
        }
    }
}
