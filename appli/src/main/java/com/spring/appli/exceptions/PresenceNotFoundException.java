package com.spring.appli.exceptions;

public class PresenceNotFoundException extends Exception {

    public PresenceNotFoundException() {
        super("Presence not found");
    }
}
