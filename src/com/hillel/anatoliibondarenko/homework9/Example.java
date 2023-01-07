package com.hillel.anatoliibondarenko.homework9;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        ValueCalculator calculator = new ValueCalculator(100_000_000);
        calculator.doCalc();

        System.out.println("=============== Many Threads ================");

        ValueCalculatorExtend calculatorExtend = new ValueCalculatorExtend(100_000_000, 16);
        calculatorExtend.doCalc();

    }
}
