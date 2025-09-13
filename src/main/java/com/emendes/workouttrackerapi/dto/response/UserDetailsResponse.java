package com.emendes.workouttrackerapi.dto.response;

import lombok.Builder;

/**
 * DTO para enviar informações detalhadas do usuário.
 */
@Builder
public record UserDetailsResponse(
    Long id,
    String email
) {
}
