package com.emendes.workouttrackerapi.dto.response;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import lombok.Builder;

/**
 * DTO para enviar informações de workout.
 */
@Builder
public record WorkoutSummaryResponse(
    Long id,
    String name,
    WorkoutStatus status
) {
}
