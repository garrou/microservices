package com.spring.participations.exceptions;

public class PresenceNotFoundException extends Exception {

    public PresenceNotFoundException() {
        super("Presence not found");
    }
}
