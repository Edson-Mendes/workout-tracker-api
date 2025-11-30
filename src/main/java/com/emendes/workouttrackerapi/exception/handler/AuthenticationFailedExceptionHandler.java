package com.emendes.workouttrackerapi.exception.handler;

import com.emendes.workouttrackerapi.dto.response.ProblemDetailResponse;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

/**
 * Classe para lidar com a exception {@code ConstraintViolationException}.
 */
@Provider
public class AuthenticationFailedExceptionHandler implements ExceptionMapper<AuthenticationFailedException> {

  @Override
  public Response toResponse(AuthenticationFailedException exception) {
    return Response
        .status(Response.Status.UNAUTHORIZED)
        .entity(generateProblemDetail(exception))
        .build();
  }

  private ProblemDetailResponse generateProblemDetail(AuthenticationFailedException exception) {
    return ProblemDetailResponse.builder()
        .title(Response.Status.UNAUTHORIZED.name())
        .description(exception.getMessage())
        .status(Response.Status.UNAUTHORIZED.getStatusCode())
        .timestamp(LocalDateTime.now())
        .build();
  }

}
