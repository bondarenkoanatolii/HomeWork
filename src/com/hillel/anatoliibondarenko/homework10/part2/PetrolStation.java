package com.hillel.anatoliibondarenko.homework10.part2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PetrolStation {
    // Константи, які обмежують об'єм заправки одного автомобіля. Впливають пропорційно на час заправки.
    private static final int MIN_REFUEL_LITER = 5;
    private static final int MAX_REFUEL_LITER = 100;

    // Константи, які обмежують час заправки одного автомобіля. Час заправки буде залежати від літрів заправки.
    private static final int MIN_TIME_REFUEL = 3_000;
    private static final int MAX_TIME_REFUEL = 10_000;

    // Кількість авто, які можуть одночасно заправлятися.
    private static final int COUNT_SIMULTANEOUS_REFUELING = 3;

    // Кількість авто, які скоро приїдуть на заправку
    private static final int COUNT_CAR_DESIRE_REFUELING = 10;

    private final Semaphore SEMAPHORE = new Semaphore(COUNT_SIMULTANEOUS_REFUELING, true);

    private final Random randomInt = new Random();

    // Ємність заправки
    private double amount;

    public PetrolStation(double amount) {
        this.amount = amount;
    }

    // Щоб не отримати неправильного залишку кількість палива, коли інший потік робить doRefuel(double desiredRefueling)
    public synchronized double getAmount() {
        return amount;
    }

    // Одночасно одна машина обслуговується на касі і бронює собі кількість палива
    private synchronized double doRefuel(double desiredRefueling) {
        if (desiredRefueling < amount) {
            amount -= desiredRefueling;
            return desiredRefueling;
        } else {
            // якщо недостатньо палива для заказу повністю, заправляється можлива решта палива.
            double possibleRefueling = amount;
            amount = 0;
            return possibleRefueling;
        }
    }

    public void work() throws InterruptedException {
        for (int i = 1; i <= COUNT_CAR_DESIRE_REFUELING; i++) {
            new Thread(new Car(i)).start();
            // Машини під'їзджають через невеликий, хоча і постійний проміжок часу, так щоб формувалася черга.
            Thread.sleep(MIN_TIME_REFUEL / COUNT_SIMULTANEOUS_REFUELING);
        }

    }

    class Car implements Runnable {
        private final int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            System.out.printf("Автомобіль №%d під'їхав до заправки.\n", carNumber);
            try {
                SEMAPHORE.acquire();

                // Підійшла черга або тільки приїхав, а паливо на запраці скінчилося.
                if (getAmount() == 0) {
                    System.out.printf("Автомобіль №%d поїхав, і не заправився. Паливо на заправці закінчилося.\n", carNumber);
                    SEMAPHORE.release();
                    return;
                }

                System.out.printf("Підійшла черга заправлятися автомобіля №%d.\n", carNumber);
                double desiredRefueling = MIN_REFUEL_LITER + randomInt.nextInt(MAX_REFUEL_LITER - MIN_REFUEL_LITER);
                System.out.printf("Автомобіль №%d хоче заправити %.1f літрів палива.\n", carNumber, desiredRefueling);

                // Зразу бронюємо бажану кількість або можливий залишок кількісті палива, щоб наступна машина
                // могла зробити замовлення, коли підійде її черга.
                double possibleRefueling = doRefuel(desiredRefueling);
                if (desiredRefueling == possibleRefueling) {
                    System.out.printf("Автомобіль №%d може повністю заправитися на %.1f літрів палива.\n"
                            , carNumber, desiredRefueling);
                } else {
                    System.out.printf("Автомобіль №%d може частково заправитися на %.1f літрів палива.\n"
                            , carNumber, possibleRefueling);
                }

                // Час заправки залежить від кількості літрів
                int timeRefuel = (int) (MIN_TIME_REFUEL + (MAX_TIME_REFUEL - MIN_TIME_REFUEL) * possibleRefueling / MAX_REFUEL_LITER);
                Thread.sleep(timeRefuel);

                SEMAPHORE.release();
                System.out.printf("Автомобіль №%d заправився на %.1f літрів й залишив заправку\n", carNumber, possibleRefueling);

            } catch (InterruptedException e) {
                // Що тут можна обробляти від SEMAPHORE.acquire() або Thread.sleep(timeRefuel) ???
                e.printStackTrace();
            }
        }
    }
}
