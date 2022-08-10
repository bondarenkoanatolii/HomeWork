package com.hillel.anatoliibondarenko.homework3;

public class Car {

    public void start() {
        startElectricity();
        startCommand();
        startFuelSystem();
    }

    private void startElectricity() {
        System.out.println("startElectricity()");
    }

    private void startCommand() {
        System.out.println("startCommand()");
    }

    private void startFuelSystem() {
        System.out.println("startFuelSystem()");
    }
}
