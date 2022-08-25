package com.hillel.anatoliibondarenko.homework5.part2;

public class Wall extends Obstacle {
    private final String name;
    private final double height;

    public Wall(String name, double height) {
        this.name = name;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
