package com.spring.competitions.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompetitionUpdateDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private CompetitionUpdateDto competition;

    @BeforeEach
    public void setUp() {
        competition = new CompetitionUpdateDto(
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
        assertTrue(validator.validate(competition).isEmpty(), "Competition must be valid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseId() {
        competition.setId(null);
        assertEquals(1, validator.validate(competition).size(), "Title must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseTitle() {
        competition.setTitle(" ");
        assertEquals(1, validator.validate(competition).size(), "Title must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseLevelMin() {
        competition.setLevel(-1);
        assertEquals(1, validator.validate(competition).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseLevelMax() {
        competition.setLevel(6);
        assertEquals(1, validator.validate(competition).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseDuration() {
        competition.setDuration(0);
        assertEquals(1, validator.validate(competition).size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseStudents() {
        competition.setStudents(
                IntStream.range(0, 600)
                        .mapToObj(i -> UUID.randomUUID())
                        .collect(Collectors.toList())
        );
        assertEquals(1, validator.validate(competition).size(), "Students must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseNbMaxStudents() {
        competition.setNbMaxStudents(0);
        assertEquals(1, validator.validate(competition).size(), "Students must be invalid");
    }
}
