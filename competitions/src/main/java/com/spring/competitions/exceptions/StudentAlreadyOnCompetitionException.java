package com.spring.competitions.exceptions;

public class StudentAlreadyOnCompetitionException extends Exception {

    public StudentAlreadyOnCompetitionException() {
        super("Student already on competition student list");
    }
}
