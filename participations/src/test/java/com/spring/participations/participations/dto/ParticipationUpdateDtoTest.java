package com.spring.participations.participations.dto;

import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParticipationUpdateDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ParticipationUpdateDto participation;

    @BeforeEach
    public void setUp() {
        participation = new ParticipationUpdateDto("test-id", "test");
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(participation).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCauseId() {
        participation.setId(" ");
        Set<ConstraintViolation<ParticipationUpdateDto>> violations = validator.validate(participation);
        assertEquals(1, violations.size(), "Id must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseCourseId() {
        participation.setCourseId(" ");
        Set<ConstraintViolation<ParticipationUpdateDto>> violations = validator.validate(participation);
        assertEquals(1, violations.size(), "CourseId must be invalid");
    }
}
