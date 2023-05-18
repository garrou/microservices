package com.spring.statistics.exceptions;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
        super("Course not found");
    }
}
