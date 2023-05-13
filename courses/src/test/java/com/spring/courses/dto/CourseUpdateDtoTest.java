package com.spring.courses.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseUpdateDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private CourseUpdateDto course;

    @BeforeEach
    public void setUp() {
        course = new CourseUpdateDto(
                "testid",
                "Toto",
                1,
                new Date(),
                120.5,
                "9 speed road",
                UUID.randomUUID(),
                List.of(UUID.randomUUID(), UUID.randomUUID()),
                18
        );
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(course).isEmpty(), "Course must be valid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseId() {
        course.setId(null);
        assertEquals(1, validator.validate(course).size(), "Title must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseTitle() {
        course.setTitle(" ");
        assertEquals(1, validator.validate(course).size(), "Title must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseLevelMin() {
        course.setLevel(-1);
        assertEquals(1, validator.validate(course).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseLevelMax() {
        course.setLevel(6);
        assertEquals(1, validator.validate(course).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseDuration() {
        course.setDuration(0);
        assertEquals(1, validator.validate(course).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseStudents() {
        course.setStudents(List.of());
        assertEquals(1, validator.validate(course).size(), "Students must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseNbStudentsMax() {
        course.setNbStudentsMax(0);
        assertEquals(1, validator.validate(course).size(), "Students must be invalid");
    }
}
