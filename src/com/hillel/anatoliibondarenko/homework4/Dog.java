package com.hillel.anatoliibondarenko.homework4;

public class Dog extends Animal {
    private static final int LIMIT_RUNNING_METER = 500;
    private static final int LIMIT_SWIMMING_METER = 10;
    private static int numberOfDogs;

    public Dog(String name) {
        super(name);
        numberOfDogs++;
    }

    @Override
    public void run(int distance) {
        super.run(Math.min(distance, LIMIT_RUNNING_METER));
    }

    @Override
    public void swim(int distance) {
        super.swim(Math.min(distance, LIMIT_SWIMMING_METER));
    }

    public static int getNumberOfDogs() {
        return numberOfDogs;
    }
}
