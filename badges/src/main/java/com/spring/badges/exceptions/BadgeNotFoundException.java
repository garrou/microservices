package com.spring.badges.exceptions;

public class BadgeNotFoundException extends Exception {

    public BadgeNotFoundException() {
        super("Badge not found");
    }
}
