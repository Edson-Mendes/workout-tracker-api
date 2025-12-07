package com.emendes.workouttrackerapi.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para enviar informações detalhadas de exercise.
 */
@Builder
public record ExerciseDetailsResponse(
    Long id,
    String name,
    int sets,
    BigDecimal currentWeight,
    List<WeightHistoryResponse> weightHistory
) {
}
