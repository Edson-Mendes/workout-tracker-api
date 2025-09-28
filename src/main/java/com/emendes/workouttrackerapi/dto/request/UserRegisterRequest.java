package com.emendes.workouttrackerapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para receber os dados de registro de usuário.
 */
public record UserRegisterRequest(
    @NotBlank(message = "email must not be blank")
    @Email
    String email,
    @NotBlank(message = "password must not be blank")
    @Size(min = 6, max = 12, message = "password must be between {min} and {max} characters long")
    String password,
    @NotBlank(message = "confirmPassword must not be blank")
    String confirmPassword
) {
}
