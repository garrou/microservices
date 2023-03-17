package com.spring.users.exceptions;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException() {
        super("User not found");
    }
}
