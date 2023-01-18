package com.hillel.anatoliibondarenko.homework8;

public class NoSuchProductTypeException extends RuntimeException {

    public NoSuchProductTypeException(String message) {
        super("Product type: " + message + " not found.");
    }
}
