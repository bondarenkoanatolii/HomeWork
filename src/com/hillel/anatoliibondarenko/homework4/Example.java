package com.hillel.anatoliibondarenko.homework4;

public class Example {

    public static void main(String[] args) {

        Animal animal = new Animal("Noname");

        Cat cat = new Cat("Barsik");
        cat.run(150);
        cat.swim(10);

        cat = new Cat("Tom");
        cat.run(300);
        cat.swim(2);

        Dog dog = new Dog("Barbos");
        dog.run(200);
        dog.swim(5);

        dog = new Dog("Tuzik");
        dog.run(400);
        dog.swim(10);

        dog = new Dog("Sirko");
        dog.run(600);
        dog.swim(15);

        System.out.printf("Number of cats - %d\n", Cat.getNumberOfCats());
        System.out.printf("Number of dogs - %d\n", Dog.getNumberOfDogs());
        System.out.printf("Number of animals - %d\n",Animal.getNumberOfAnimals());
    }
}
