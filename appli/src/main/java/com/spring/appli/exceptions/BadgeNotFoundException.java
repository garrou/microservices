package com.spring.appli.exceptions;

public class BadgeNotFoundException extends Exception {

    public BadgeNotFoundException() {
        super("Badge not found");
    }
}
