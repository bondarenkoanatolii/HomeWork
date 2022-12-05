package com.hillel.anatoliibondarenko.homework12;

public class Author {
    // private int id;
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Author(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
}
