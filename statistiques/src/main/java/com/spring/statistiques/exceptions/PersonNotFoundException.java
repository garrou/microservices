package com.spring.statistiques.exceptions;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException() {
        super("User not found");
    }
}
