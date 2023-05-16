package com.spring.statistiques.exceptions;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
        super("Course not found");
    }
}
