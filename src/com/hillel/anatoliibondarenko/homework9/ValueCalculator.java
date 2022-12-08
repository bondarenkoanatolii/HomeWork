package com.hillel.anatoliibondarenko.homework9;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ValueCalculator {
    private double[] array;
    private int size;
    private int halfSize;

    public ValueCalculator(int size) {
        this.size = size;
        array = new double[size];
        halfSize = size / 2;
    }

    class Part implements Runnable {
        private double[] arr;
        private int startPos;
        private int quantity;

        public Part(double[] arr, int startPos, int quantity) {
            this.startPos = startPos;
            this.quantity = quantity;
            this.arr = arr;
        }

        @Override
        public void run() {
            int realIndex;
            for (int i = 0; i < quantity; i++) {
                realIndex = startPos + i;
                arr[i] = (float) (arr[i] * Math.sin(0.2f + realIndex / 5) * Math.cos(0.2f + realIndex / 5)
                        * Math.cos(0.4f + realIndex / 2));
            }
        }
    }

    void doCalc() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }

        for (int i = 0; i < size; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        double[] array2 = new double[size];
        for (int i = 0; i < size; i++) {
            array2[i] = 1;
        }

        int numberOfThreads = 3;
        int sizeSubArray = size / numberOfThreads;
        List<double[]> list = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            if (i != numberOfThreads - 1) {
                list.add(new double[sizeSubArray]);
                System.arraycopy(array2, i * sizeSubArray, list.get(i), 0, sizeSubArray);

            } else {
                list.add(new double[size - i * sizeSubArray]);
                System.arraycopy(array2, i * sizeSubArray, list.get(i), 0, size - i * sizeSubArray);

            }
        }

        //
       // double[] arr[] = new double[2][];


//        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);
//        for (int i = 0; i < numberOfThreads; i++) {
//            exec.execute(new Part(list.get(i), 0 , halfSize));
//        }

//        List<double[]> list = new ArrayList<>();
//        list.add(new double[halfSize]);
//        list.add(new double[size - halfSize]);
//
//        System.arraycopy(array2, 0, list.get(0), 0, halfSize);
//        System.arraycopy(array2, halfSize, list.get(1), 0, size - halfSize);

        Thread part1 = new Thread(new Part(list.get(0), 0 , sizeSubArray));
        Thread part2 = new Thread(new Part(list.get(1), sizeSubArray , sizeSubArray));
        Thread part3 = new Thread(new Part(list.get(2), 2 * sizeSubArray , size - 2 * sizeSubArray));

        part1.start();
        part2.start();
        part3.start();

        part1.join();
        part2.join();
        part3.join();

        System.arraycopy(list.get(0), 0, array2, 0, sizeSubArray);
        System.arraycopy(list.get(1), 0, array2, sizeSubArray, sizeSubArray);
        System.arraycopy(list.get(2), 0, array2, 2 * sizeSubArray, size - 2 * sizeSubArray);

        //        double arrayOne[]  = new double[halfSize];
//        double arrayTwo[]  = new double[size - halfSize];

//        System.arraycopy(array2, 0, arrayOne, 0, halfSize);
//        System.arraycopy(array2, halfSize, arrayTwo, 0, size - halfSize);

//        Thread part1 = new Thread(new Part(arrayOne, 0 , halfSize));
//        Thread part2 = new Thread(new Part(arrayTwo, halfSize , size - halfSize));



        //double[] array2 = new double[size];
//        System.arraycopy(arrayOne, 0, array2, 0, halfSize);
//        System.arraycopy(arrayTwo, 0, array2, halfSize, size - halfSize);

        end = System.currentTimeMillis();
        System.out.println(end - start);

        for (int i = 0; i < size; i++) {
            if (array[i] != array2[i]) {
                System.out.println(i + " " + array[i] + " " + array2[i]);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ValueCalculator calculator = new ValueCalculator(150_000_000);
        calculator.doCalc();
    }

}
