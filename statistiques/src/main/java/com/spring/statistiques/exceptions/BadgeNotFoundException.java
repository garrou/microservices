package com.spring.statistiques.exceptions;

public class BadgeNotFoundException extends Exception {

    public BadgeNotFoundException() {
        super("Badge not found");
    }
}
