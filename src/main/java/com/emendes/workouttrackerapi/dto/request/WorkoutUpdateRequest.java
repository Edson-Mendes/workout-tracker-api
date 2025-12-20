package com.emendes.workouttrackerapi.dto.request;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.validation.annotation.WorkoutStatusValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO para receber os dados de registro de workout.
 */
@Builder
public record WorkoutUpdateRequest(
    @NotBlank(message = "name must not be blank")
    String name,
    @NotBlank(message = "status must not be blank")
    @WorkoutStatusValidation(message = "status must be ['ONGOING', 'FINISHED']")
    String status
) {

  public WorkoutStatus getStatus() {
    return WorkoutStatus.valueOf(this.status);
  }

}
