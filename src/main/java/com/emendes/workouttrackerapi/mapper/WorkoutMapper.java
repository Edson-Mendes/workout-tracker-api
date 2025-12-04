package com.emendes.workouttrackerapi.mapper;

import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseSummaryResponse;
import com.emendes.workouttrackerapi.dto.response.WorkoutDetailsResponse;
import com.emendes.workouttrackerapi.dto.response.WorkoutSummaryResponse;
import com.emendes.workouttrackerapi.model.entity.Workout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Mapper responsável pelo mapeamento de classes relacionadas a Workout.
 */
@ApplicationScoped
public class WorkoutMapper {

  /**
   * Mepeia um objeto workoutRegisterRequest para Workout.
   */
  public Workout toWorkout(@Valid WorkoutRegisterRequest workoutRegisterRequest) {
    if (workoutRegisterRequest == null)
      throw new IllegalArgumentException("workoutRegisterRequest must not be null");

    return Workout.builder()
        .name(workoutRegisterRequest.name())
        .build();
  }

  /**
   * Mapeia um objeto Workout para WorkoutSummaryResponse.
   */
  public WorkoutSummaryResponse toWorkoutSummaryResponse(Workout workout) {
    if (workout == null)
      throw new IllegalArgumentException("workout must not be null");

    return WorkoutSummaryResponse.builder()
        .id(workout.getId())
        .name(workout.getName())
        .status(workout.getStatus())
        .build();
  }

  /**
   * Mapeia um objeto {@code Workout} e uma {@code List<ExerciseSummaryResponse>}
   * para {@code WorkoutSummaryResponse}.
   */
  public WorkoutDetailsResponse toWorkoutDetailsResponse(Workout workout, List<ExerciseSummaryResponse> exercises) {
    if (workout == null)
      throw new IllegalArgumentException("workout must not be null");
    if (exercises == null)
      throw new IllegalArgumentException("exercises must not be null");

    return WorkoutDetailsResponse.builder()
        .id(workout.getId())
        .name(workout.getName())
        .status(workout.getStatus())
        .exercises(exercises)
        .build();
  }

}
