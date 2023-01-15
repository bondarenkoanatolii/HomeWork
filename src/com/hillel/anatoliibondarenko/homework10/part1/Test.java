package com.hillel.anatoliibondarenko.homework10.part1;

import java.util.Random;
import java.util.concurrent.*;

/**
 *      As a test in each of the threads, we will do COUNT_OPERATIONS_ON_THREAD_SAFE_LIST times of random
 *  ADD, REMOVE, GET operations on ThreadSafeList:
 *      1. We will insert random positive integers within MAX_VALUE_ELEMENT_TO_ADD and count the total amount we added
 *  and the number of elements we added.
 *      2. We will delete a random element by number (randomly from the number of added elements, then the index
 *  of such elements is independent of other threads) and subtract its value from the sum of the added numbers
 *  of the thread. We reduce the number of added elements.
 *      3. We will read a random element (again we randomly select it from the number of added elements)
 *  COUNT_GET_OPERATION times.
 *      As a result, we have a number - the result of the thread activity (the sum of the added elements minus
 *  the sum of the deleted ones)
 */
public class Test {
    // The number of threads that will work with the ThreadSafeList.
    private static final int NUMBER_OF_THREADS = 10;

    // The number of operations that will be performed on the ThreadSafeList by each thread.
    private static final int COUNT_OPERATIONS_ON_THREAD_SAFE_LIST = 100;

    // The maximum value that will be added to the ThreadSafeList.
    private static final int MAX_VALUE_ELEMENT_TO_ADD = 100;

    // We believe that we will work with the collection more for reading than for adding a new element or deleting
    // an existing one. Therefore, we will read the ThreadSafeList collection COUNT_GET_OPERATION times in a row.
    private static final int COUNT_GET_OPERATION = 100;

    // Latch for simultaneous launch of all threads.
    static final CountDownLatch startGate = new CountDownLatch(1);


    private static final Future<Integer>[] future = new Future[NUMBER_OF_THREADS];

    static ThreadSafeList<Integer> threadSafeList = new ThreadSafeList<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
             future[i] = executors.submit(new Task());
        }
        executors.shutdown();

        startGate.countDown();

        // Let's output the results of the work of each thread as the value of future.get().
        System.out.println("View threads results: ");
        System.out.println(toStringFutureArray(future));

        //  The overall result of all threads.
        int sumFromFuture = 0;
        for (Future<Integer> element : future) {
            sumFromFuture += element.get();
        }
        System.out.println("Sum of all threads result: " + sumFromFuture);

        System.out.println("View ThreadSafeList after all threads are running:");
        System.out.println(threadSafeList);
        int sumThreadSafeList = sumThreadSafeList();
        System.out.println("Sum its elements: " + sumThreadSafeList);

        if (sumFromFuture == sumThreadSafeList()) {
            System.out.println("All operations in all threads are saved.");
        } else {
            System.out.println("Somethings went wrong.");
        }

    }

    private static class Task implements Callable<Integer> {
        private final Random randomInt = new Random();
        private int amount = 0;
        private int numberElementAdded = 0;

        @Override
        public Integer call() {
            try {
                startGate.await();
                for (int i = 0; i < COUNT_OPERATIONS_ON_THREAD_SAFE_LIST; i++) {
                    int element;
                    Operation operation = Operation.randomOperation();

                    switch (operation) {
                        case ADD: {
                            element = randomInt.nextInt(MAX_VALUE_ELEMENT_TO_ADD);
                            threadSafeList.add(element);
                            amount += element;
                            numberElementAdded++;
                            break;
                        }
                        case REMOVE: {
                            if (numberElementAdded > 0) {
                                element = threadSafeList.remove(randomInt.nextInt(numberElementAdded));
                                amount -= element;
                                numberElementAdded--;
                            }
                            break;
                        }
                        case GET: {
                            if (numberElementAdded > 0) {
                                for (int j = 0; j < COUNT_GET_OPERATION; j++) {
                                    threadSafeList.get(randomInt.nextInt(numberElementAdded));
                                }
                            }
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return amount;
        }
    }

    /**
     * ThreadSafeList hasn't iterator. We iterate until IndexOutOfBoundsException is catching.
     *
     */
    private static int sumThreadSafeList() {
        boolean isEndThreadSafeList = false;
        int pos = 0;
        int sumFromThreadSafeList = 0;
        while (!isEndThreadSafeList) {
            try {
                sumFromThreadSafeList += threadSafeList.get(pos);
                pos++;
            } catch (IndexOutOfBoundsException e) {
                isEndThreadSafeList = true;
            }
        }
        return sumFromThreadSafeList;
    }

    private static String toStringFutureArray(Future<?>[] future) throws ExecutionException, InterruptedException {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; i < future.length; i++) {
            b.append(future[i].get());
            if (i == future.length - 1) {
                b.append(']');
            } else {
                b.append(", ");
            }
        }
        return b.toString();
    }
}
