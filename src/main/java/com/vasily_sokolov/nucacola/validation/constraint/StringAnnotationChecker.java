package com.vasily_sokolov.nucacola.validation.constraint;

import com.vasily_sokolov.nucacola.validation.interf.Str45LengthCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringAnnotationChecker implements ConstraintValidator<Str45LengthCheck, String> {
    @Override
    public void initialize(Str45LengthCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return s.length() < 45 && s.length() > 2;
    }
}
