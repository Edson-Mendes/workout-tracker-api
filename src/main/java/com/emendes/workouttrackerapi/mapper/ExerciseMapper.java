package com.emendes.workouttrackerapi.mapper;

import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseDetailsResponse;
import com.emendes.workouttrackerapi.dto.response.ExerciseSummaryResponse;
import com.emendes.workouttrackerapi.dto.response.WeightHistoryResponse;
import com.emendes.workouttrackerapi.model.entity.Exercise;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Mapper responsável pelo mapeamento de classes relacionadas a Exercise.
 */
@ApplicationScoped
public class ExerciseMapper {

  /**
   * Mepeia objeto ExerciseRegisterRequest para Exercise.
   */
  public Exercise toExercise(ExerciseRegisterRequest exerciseRegisterRequest) {
    if (exerciseRegisterRequest == null)
      throw new IllegalArgumentException("exercise must not be null");

    return Exercise.builder()
        .name(exerciseRegisterRequest.name())
        .sets(exerciseRegisterRequest.sets())
        .weight(exerciseRegisterRequest.weight())
        .build();
  }

  /**
   * Mapeia objeto Exercise para ExerciseSummaryResponse.
   */
  public ExerciseSummaryResponse toExerciseSummaryResponse(Exercise exercise) {
    if (exercise == null)
      throw new IllegalArgumentException("exercise must not be null");

    return ExerciseSummaryResponse.builder()
        .id(exercise.getId())
        .name(exercise.getName())
        .sets(exercise.getSets())
        .weight(exercise.getWeight())
        .build();
  }

  /**
   * Mapeia objeto Exercise para ExerciseDetailsResponse.
   */
  public static ExerciseDetailsResponse toExerciseDetailsResponse(Exercise exercise, List<WeightHistoryResponse> weightHistoryResponseList) {
    if (exercise == null)
      throw new IllegalArgumentException("exercise must not be null");
    if (weightHistoryResponseList == null)
      throw new IllegalArgumentException("weightHistoryResponseList must not be null");

    return ExerciseDetailsResponse.builder()
        .id(exercise.getId())
        .name(exercise.getName())
        .sets(exercise.getSets())
        .currentWeight(exercise.getWeight())
        .weightHistory(weightHistoryResponseList)
        .build();
  }

  /**
   * Mescla os dados de exercise com os dados de exerciseRegisterRequest.
   */
  public void merge(Exercise exercise, ExerciseRegisterRequest exerciseRegisterRequest) {
    if (exercise == null)
      throw new IllegalArgumentException("exercise must not be null");
    if (exerciseRegisterRequest == null)
      throw new IllegalArgumentException("exerciseRegisterRequest must not be null");

    exercise.setName(exerciseRegisterRequest.name());
    exercise.setSets(exerciseRegisterRequest.sets());
    exercise.setWeight(exerciseRegisterRequest.weight());
  }
}
