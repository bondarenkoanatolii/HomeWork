package com.hillel.anatoliibondarenko.homework4;

public class Cat extends Animal {
    private static final int LIMIT_RUNNING_METER = 200;
    private static int numberOfCats;

    public Cat(String name) {
        super(name);
        numberOfCats++;
    }

    public static int getNumberOfCats() {
        return numberOfCats;
    }

    @Override
    public void run(int distance) {
        distance = Math.min(distance, LIMIT_RUNNING_METER);
        super.run(distance);
    }

    @Override
    public void swim(int distance) {
        System.out.println("Cats can't swim!");
    }
}
