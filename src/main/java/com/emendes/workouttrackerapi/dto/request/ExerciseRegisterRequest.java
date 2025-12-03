package com.emendes.workouttrackerapi.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * DTO para receber os dados de registro de workout.
 */
@Builder
public record ExerciseRegisterRequest(
    @NotBlank(message = "name must not be blank")
    String name,
    @Positive(message = "sets must be positive")
    int sets,
    @NotNull(message = "weight must not be null")
    @Positive(message = "weight must be positive")
    @Digits(integer = 3, fraction = 1, message = "weight must contain max {integer} integral digits and max {fraction} fractional digits")
    BigDecimal weight
) {
}
