package com.vasily_sokolov.nucacola.validation.constraint;

import com.vasily_sokolov.nucacola.validation.interf.UuidCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UuidAnnotationChecker implements ConstraintValidator<UuidCheck, String> {

    private static final String TEMPLATE =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";


    @Override
    public void initialize(UuidCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return s.matches(TEMPLATE);
    }
}
