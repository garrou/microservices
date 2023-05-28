package com.spring.users.exceptions;

public class WrongAuthentificationException extends Exception {

    public WrongAuthentificationException() {
        super("Pseudo/password don't match");
    }
}
