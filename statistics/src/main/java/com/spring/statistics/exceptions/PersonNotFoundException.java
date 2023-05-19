package com.spring.statistics.exceptions;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException() {
        super("User not found");
    }
}
