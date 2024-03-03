package com.vasily_sokolov.nucacola.validation.interf;

import com.vasily_sokolov.nucacola.validation.constraint.UuidAnnotationChecker;
import com.vasily_sokolov.nucacola.validation.constraint.UuidAnnotationCheckerUUID;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UuidAnnotationCheckerUUID.class})
public @interface UuidCheckUUID {
    String message() default "UUID it`s not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
