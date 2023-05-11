package com.spring.participations.exceptions;

public class ParticipationNotFoundException extends Exception {

    public ParticipationNotFoundException() {
        super("Participation not found");
    }
}
