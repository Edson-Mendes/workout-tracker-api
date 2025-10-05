package com.emendes.workouttrackerapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO para receber os dados de registro de workout.
 */
@Builder
public record WorkoutRegisterRequest(
    @NotBlank(message = "name must not be blank")
    String name
) {
}
