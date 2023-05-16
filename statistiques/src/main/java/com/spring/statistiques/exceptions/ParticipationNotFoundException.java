package com.spring.statistiques.exceptions;

public class ParticipationNotFoundException extends Exception {

    public ParticipationNotFoundException() {
        super("Participation not found");
    }
}
