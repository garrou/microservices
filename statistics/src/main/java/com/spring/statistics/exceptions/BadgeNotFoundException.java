package com.spring.statistics.exceptions;

public class BadgeNotFoundException extends Exception {

    public BadgeNotFoundException() {
        super("Badge not found");
    }
}
