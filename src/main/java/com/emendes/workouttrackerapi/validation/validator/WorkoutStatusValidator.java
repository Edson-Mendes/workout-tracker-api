package com.emendes.workouttrackerapi.validation.validator;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.validation.annotation.WorkoutStatusValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Valida se a String é um dos valores ['ONGOING', 'FINISHED'].
 */
public class WorkoutStatusValidator implements ConstraintValidator<WorkoutStatusValidation, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) return true;
    try {
      WorkoutStatus.valueOf(value);
      return true;
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }
}
