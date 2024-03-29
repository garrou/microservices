package com.spring.users.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonUpdateDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private PersonUpdateDto person;

    @BeforeEach
    public void setUp() {
        person = new PersonUpdateDto(
                UUID.randomUUID(),
                "Toto",
                "Tata",
                "tt",
                "tt@gmail.com",
                "9 speed road",
                4,
                "TEACHER",
                "test"
        );
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(person).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCauseId() {
        person.setId(null);
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Id must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseFirstname() {
        person.setFirstname(" ");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Firstname must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseLastname() {
        person.setLastname(" ");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Lastname must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCausePseudo() {
        person.setPseudo(" ");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Pseudo must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseEmail() {
        person.setEmail("invalid");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Email must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseAddress() {
        person.setAddress(" ");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Address must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseMaxLevel() {
        person.setLevel(9);
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseMinLevel() {
        person.setLevel(-1);
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Level must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCauseRole() {
        person.setRole("");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Role must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCausePassword() {
        person.setPassword("");
        Set<ConstraintViolation<PersonUpdateDto>> violations = validator.validate(person);
        assertEquals(1, violations.size(), "Password must be invalid");
    }
}
