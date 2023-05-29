package com.spring.appli.exceptions;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
        super("Course not found");
    }
}
