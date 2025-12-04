package com.emendes.workouttrackerapi.dto.response;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import lombok.Builder;

import java.util.List;

/**
 * DTO para enviar informações detalhadas de workout.
 */
@Builder
public record WorkoutDetailsResponse(
    Long id,
    String name,
    WorkoutStatus status,
    List<ExerciseSummaryResponse> exercises
) {
}
