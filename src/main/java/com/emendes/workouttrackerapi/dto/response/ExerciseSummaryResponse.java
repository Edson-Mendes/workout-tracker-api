package com.emendes.workouttrackerapi.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * DTO para enviar informações resumida de exercise.
 */
@Builder
public record ExerciseSummaryResponse(
    Long id,
    String name,
    int sets,
    BigDecimal weight
) {
}
