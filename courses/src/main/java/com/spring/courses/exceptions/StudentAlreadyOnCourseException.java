package com.spring.courses.exceptions;

public class StudentAlreadyOnCourseException extends Exception {

    public StudentAlreadyOnCourseException() {
        super("Student already on course student list");
    }
}
