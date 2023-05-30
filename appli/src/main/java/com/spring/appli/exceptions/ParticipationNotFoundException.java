package com.spring.appli.exceptions;

public class ParticipationNotFoundException extends Exception {

    public ParticipationNotFoundException() {
        super("Participation not found");
    }
}
