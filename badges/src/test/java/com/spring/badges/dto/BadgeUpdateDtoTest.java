package com.spring.badges.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BadgeUpdateDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private BadgeUpdateDto badge;

    @BeforeEach
    public void setUp() {
        badge = new BadgeUpdateDto("test", "person-id");
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(badge).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCauseBadgeId() {
        badge.setId(" ");
        Set<ConstraintViolation<BadgeUpdateDto>> violations = validator.validate(badge);
        assertEquals(1, violations.size(), "Id must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCausePersonId() {
        badge.setIdPerson(" ");
        Set<ConstraintViolation<BadgeUpdateDto>> violations = validator.validate(badge);
        assertEquals(1, violations.size(), "CourseId must be invalid");
    }
}
