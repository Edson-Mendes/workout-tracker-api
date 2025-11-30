package com.emendes.workouttrackerapi.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para enviar informações detalhadas em caso de erro.
 */
@Builder
public record ProblemDetailResponse(
    String title,
    String description,
    int status,
    LocalDateTime timestamp,
    @JsonAnyGetter
    Map<String, Object> others
) {
}
