package com.hillel.anatoliibondarenko.homework4;

public class Animal {
    private String name;
    private static int numberOfAnimals;

    public Animal(String name) {
        this.name = name;
        numberOfAnimals++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
         return name;
    }

    public static int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void run(int distance) {
        printMessage("ran", distance);
    }

    public void swim(int distance) {
        printMessage("swam", distance);
    }

    private void printMessage(String action, int distance) {
        System.out.printf("%s %s %d m\n", name, action, distance);
    }
}
