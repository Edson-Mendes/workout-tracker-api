package com.emendes.workouttrackerapi.exception.handler;

import com.emendes.workouttrackerapi.dto.response.ProblemDetailResponse;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

/**
 * Classe para lidar com a exception {@code WebApplicationException}.
 */
@Provider
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException> {

  @Override
  public Response toResponse(WebApplicationException exception) {
    return Response
        .status(exception.getResponse().getStatus())
        .entity(generateProblemDetail(exception))
        .build();
  }

  private ProblemDetailResponse generateProblemDetail(WebApplicationException exception) {
    int statusCode = exception.getResponse().getStatus();
    return ProblemDetailResponse.builder()
        .title(Response.Status.fromStatusCode(statusCode).name())
        .description(exception.getMessage())
        .status(statusCode)
        .timestamp(LocalDateTime.now())
        .build();
  }

}
