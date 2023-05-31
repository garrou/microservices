package com.spring.appli.exceptions;

public class WrongAuthentificationException extends Exception {

    public WrongAuthentificationException() {
        super("Pseudo/password don't match");
    }
}
