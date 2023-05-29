package com.spring.appli.exceptions;

public class CompetitionNotFoundException extends Exception {

    public CompetitionNotFoundException() {
        super("Competition not found");
    }
}
