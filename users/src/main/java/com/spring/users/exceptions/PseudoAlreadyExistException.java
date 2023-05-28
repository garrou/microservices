package com.spring.users.exceptions;

public class PseudoAlreadyExistException extends Exception {

    public PseudoAlreadyExistException() {
        super("Pseudo already exists");
    }
}
