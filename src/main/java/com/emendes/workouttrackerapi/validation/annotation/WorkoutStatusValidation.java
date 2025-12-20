package com.emendes.workouttrackerapi.validation.annotation;

import com.emendes.workouttrackerapi.validation.validator.WorkoutStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * O elemento anotado deve ser 'ONGOING', 'FINISHED'.<br>
 * <br>
 * Elementos nulos são considerados válidos.
 */
@Constraint(validatedBy = {WorkoutStatusValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface WorkoutStatusValidation {

  String message() default "must be ['ONGOING', 'FINISHED']";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
