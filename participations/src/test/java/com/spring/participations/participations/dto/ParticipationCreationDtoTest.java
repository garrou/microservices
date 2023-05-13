package com.spring.participations.participations.dto;

import com.spring.participations.dto.ParticipationCreationDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParticipationCreationDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ParticipationCreationDto participation;

    @BeforeEach
    public void setUp() {
        participation = new ParticipationCreationDto("test");
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(participation).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCauseCourseId() {
        participation.setCourseId(" ");
        Set<ConstraintViolation<ParticipationCreationDto>> violations = validator.validate(participation);
        assertEquals(1, violations.size(), "CourseId must be invalid");
    }
}
