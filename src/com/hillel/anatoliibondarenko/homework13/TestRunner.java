package com.hillel.anatoliibondarenko.homework13;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class TestRunner {

    public static void start(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        try {
            checkNullClass(clazz);

            Method[] methods = clazz.getDeclaredMethods();
            checkClassHasTests(clazz, methods);

            checkProperlyCountTests(clazz, methods);

            Constructor<?> cons = clazz.getConstructor();
            Object newInstance = cons.newInstance();
            runTests(newInstance, methods);
        } catch (TestRunnerException e) {
            throw new TestRunnerException(e.getMessage());
        }
    }

    private static void runTests(Object newInstance, Method[] methods) {
        Arrays.stream(methods)
                // Фільтр на методи. Можливо будуть присутні ще якісь неаннотовані методи.
                .filter(x -> (x.isAnnotationPresent(Test.class) || x.isAnnotationPresent(BeforeSuite.class))
                        || x.isAnnotationPresent(AfterSuite.class))
                // Метод BeforeSuite - найнижчий пріорітет, AfterSuite - найвищий. Методи Test пріорітет по priority().
                .sorted((x1, x2) -> {
                    if (x2.isAnnotationPresent(AfterSuite.class) || x1.isAnnotationPresent(BeforeSuite.class)) {
                        return -1;
                    } else if (x1.isAnnotationPresent(AfterSuite.class) || x2.isAnnotationPresent(BeforeSuite.class)) {
                        return 1;
                    } else {
                        return x1.getAnnotation(Test.class).priority() - x2.getAnnotation(Test.class).priority();
                    }
                })
                .forEach(x -> {
                    // Просто x.invoke(newInstance) без try-catch компілятор не пропускає.
                    try {
                        if (x.isAnnotationPresent(Test.class)) {
                            System.out.print("Priority: " + x.getAnnotation(Test.class).priority() + " - ");
                        }
                        x.invoke(newInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static int countAnnotationInClass(Method[] methods, Class<? extends Annotation> annotation) {
        int count = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                count++;
            }
        }
        return count;
    }

    private static void checkNullClass(Class<?> clazz) {
        if (clazz == null) {
            throw new TestRunnerException("No class to test");
        }
    }

    private static void checkClassHasTests(Class<?> clazz, Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class) || method.isAnnotationPresent(Test.class)
                    || method.isAnnotationPresent(AfterSuite.class)) {
                return;
            }
        }
        throw new TestRunnerException(clazz.getName() + " don`t have annotated tests.");
    }

    private static void checkProperlyCountTests(Class<?> clazz, Method[] methods) {
        int countBeforeSuite = countAnnotationInClass(methods, BeforeSuite.class);
        int countAfterSuite = countAnnotationInClass(methods, AfterSuite.class);
        if (countBeforeSuite > 1 || countAfterSuite > 1) {
            throw new TestRunnerException(clazz.getSimpleName() + " can't pass TestRunner.\n"
                    + " Count of methods with annotations BeforeSuite or AfterSuite is more than one.");
        }
    }
}
