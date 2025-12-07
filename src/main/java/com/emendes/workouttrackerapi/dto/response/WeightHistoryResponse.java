package com.emendes.workouttrackerapi.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para enviar informações de WeightHistory.
 */
@Builder
public record WeightHistoryResponse(
    Long id,
    BigDecimal weight,
    LocalDateTime date
) {
}
