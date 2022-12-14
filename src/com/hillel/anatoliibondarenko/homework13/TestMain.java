package com.hillel.anatoliibondarenko.homework13;

import java.lang.reflect.InvocationTargetException;

public class TestMain {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        System.out.println("Test ClassTest1");
        TestRunner.start(ClassTest1.class);
        System.out.println("=======================================================");

        System.out.println("Test ClassTest2");
        TestRunner.start(ClassTest2.class);
        System.out.println("=======================================================");

        System.out.println("Test ClassTest3");
        TestRunner.start(ClassTest3.class);
        System.out.println("=======================================================");

        // Три різні випадки винятків.

        // Exception 1.
        System.out.println("Test ClassTest4");
        TestRunner.start(ClassTest4.class);
        System.out.println("=======================================================");

        // Exception 2.
//        System.out.println("Null");
//        TestRunner.start(null);
//        System.out.println("=======================================================");

        // Exception 3.
//        System.out.println("Test unsuitable class");
//        TestRunner.start(String.class);
//        System.out.println("=======================================================");

    }
}
