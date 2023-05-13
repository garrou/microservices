package com.spring.badges.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BadgeCreationDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private BadgeCreationDto badge;

    @BeforeEach
    public void setUp() {
        badge = new BadgeCreationDto("test");
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(badge).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCausePersonId() {
        badge.setIdPerson(" ");
        Set<ConstraintViolation<BadgeCreationDto>> violations = validator.validate(badge);
        assertEquals(1, violations.size(), "PersonId must be invalid");
    }
}
