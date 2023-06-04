package com.spring.appli.exceptions;

public class BadTokenException extends Exception {

    public BadTokenException() {
        super("Invalid token");
    }
}
