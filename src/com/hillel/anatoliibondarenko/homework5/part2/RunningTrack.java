package com.hillel.anatoliibondarenko.homework5.part2;

public class RunningTrack extends Obstacle {
    private final String name;
    private final double length;

    public RunningTrack(String name, double length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }
}
