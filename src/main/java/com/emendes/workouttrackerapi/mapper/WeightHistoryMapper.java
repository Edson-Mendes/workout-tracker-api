package com.emendes.workouttrackerapi.mapper;

import com.emendes.workouttrackerapi.dto.response.WeightHistoryResponse;
import com.emendes.workouttrackerapi.model.entity.Exercise;
import com.emendes.workouttrackerapi.model.entity.WeightHistory;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

/**
 * Mapper responsável pelo mapeamento de classes relacionadas a WeightHistory.
 */
@ApplicationScoped
public class WeightHistoryMapper {

  /**
   * Mepeia objeto Exercise para WeightHistory.
   */
  public WeightHistory toWeightHistory(Exercise exercise) {
    if (exercise == null)
      throw new IllegalArgumentException("exerciseRegisterRequest must not be null");

    return WeightHistory.builder()
        .value(exercise.getWeight())
        .createdAt(LocalDateTime.now())
        .exercise(exercise)
        .build();
  }

  /**
   * Mapeia objeto WeightHistory para WeightHistoryResponse.
   */
  public WeightHistoryResponse toWeightHistoryResponse(WeightHistory weightHistory) {
    if (weightHistory == null)
      throw new IllegalArgumentException("weightHistory must not be null");

    return WeightHistoryResponse.builder()
        .id(weightHistory.getId())
        .weight(weightHistory.getValue())
        .date(weightHistory.getCreatedAt())
        .build();
  }

}
