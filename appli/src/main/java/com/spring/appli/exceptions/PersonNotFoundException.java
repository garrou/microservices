package com.spring.appli.exceptions;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException() {
        super("User not found");
    }
}
