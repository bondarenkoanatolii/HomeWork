package com.hillel.anatoliibondarenko.homework5.part1;

public class Example {
    public static void main(String[] args) {
        GeometricFigure[] figures = new GeometricFigure[6];
        figures[0] = new Circle(5);
        figures[1] = new Triangle(3, 4, 5);
        figures[2] = new Square(10);
        figures[3] = new Circle(6.5);
        figures[4] = new Triangle(5.6, 8.5, 10.7);
        figures[5] = new Square(7.7);

        System.out.println(sumArea(figures));
    }

    public static double sumArea(GeometricFigure[] figures) {
        double sum = 0.0;
        for (GeometricFigure figure : figures) sum += figure.area();
        return sum;
    }
}
