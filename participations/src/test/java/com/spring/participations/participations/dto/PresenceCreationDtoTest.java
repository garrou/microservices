package com.spring.participations.participations.dto;

import com.spring.participations.dto.PresenceCreationDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PresenceCreationDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private PresenceCreationDto presence;

    @BeforeEach
    public void setUp() {
        presence = new PresenceCreationDto("test", false, 10.5);
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(presence).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCauseNoteMin() {
        presence.setNote(-1.0);
        Set<ConstraintViolation<PresenceCreationDto>> violations = validator.validate(presence);
        assertEquals(1, violations.size(), "Note must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseNoteMax() {
        presence.setNote(21.0);
        Set<ConstraintViolation<PresenceCreationDto>> violations = validator.validate(presence);
        assertEquals(1, violations.size(), "Note must be invalid");
    }
}
