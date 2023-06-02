package com.spring.appli.exceptions;

public class PseudoAlreadyExistException extends Exception {

    public PseudoAlreadyExistException() {
        super("Pseudo already exists");
    }
}
