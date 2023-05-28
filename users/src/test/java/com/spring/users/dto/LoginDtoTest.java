package com.spring.users.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDto(
                "tt",
                "test"
        );
    }

    @Test
    public void validateTestShouldBeValid() {
        assertTrue(validator.validate(loginDto).isEmpty());
    }

    @Test
    public void validateTestShouldBeInvalidCausePseudo() {
        loginDto.setPseudo(" ");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        assertEquals(1, violations.size(), "Pseudo must be invalid");
    }

    @Test
    public void validateTestShouldBeInvalidCausePassword() {
        loginDto.setPassword("");
        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
        assertEquals(1, violations.size(), "Password must be invalid");
    }
}
