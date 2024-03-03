package com.vasily_sokolov.nucacola.validation.constraint;

import com.vasily_sokolov.nucacola.validation.interf.UuidCheckUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UuidAnnotationCheckerUUID implements ConstraintValidator<UuidCheckUUID, UUID> {
    private static final String TEMPLATE =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";


    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        if (uuid == null) {
            return false;
        }
        return String.valueOf(uuid).matches(TEMPLATE);
    }
}

