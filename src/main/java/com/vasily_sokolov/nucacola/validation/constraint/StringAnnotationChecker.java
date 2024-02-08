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
        return s.length() < 45;
        //  return Optional.ofNullable(s)
        //.filter(el->!el.isBlank()) //возращает фолс емли елем пустой
        //     .map(el -> el.length() > 45)
        //    .orElse(false);  // если пошло что то не так
    }
}
