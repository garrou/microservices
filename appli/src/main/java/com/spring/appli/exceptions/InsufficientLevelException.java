package com.spring.appli.exceptions;

public class InsufficientLevelException extends Exception {

    public InsufficientLevelException() {
        super("The user doesn't have the level to create a course upper that is current level");
    }
}
