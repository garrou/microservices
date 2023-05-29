package com.spring.appli.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UuidValidator implements ConstraintValidator<Uuid, UUID> {

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        try {
            UUID.fromString(value.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
