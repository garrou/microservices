package com.spring.competitions.exceptions;

public class CompetitionNotFoundException extends Exception {

    public CompetitionNotFoundException() {
        super("Competition not found");
    }
}
