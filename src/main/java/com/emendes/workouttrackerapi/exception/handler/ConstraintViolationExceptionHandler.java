package com.emendes.workouttrackerapi.exception.handler;

import com.emendes.workouttrackerapi.dto.response.ProblemDetailResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe para lidar com a exception {@code ConstraintViolationException}.
 */
@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(generateProblemDetail(exception))
        .build();
  }

  private ProblemDetailResponse generateProblemDetail(ConstraintViolationException exception) {
    Map<String, Object> map = new HashMap<>();
    String messages = exception.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.joining(";"));
    String fields = exception.getConstraintViolations().stream()
        .map(ConstraintViolation::getPropertyPath)
        .map(this::extractLast)
        .collect(Collectors.joining(";"));

    map.put("messages", messages);
    map.put("fields", fields);

    return ProblemDetailResponse.builder()
        .title(Response.Status.BAD_REQUEST.name())
        .description("Some fields are invalid")
        .status(Response.Status.BAD_REQUEST.getStatusCode())
        .timestamp(LocalDate.now())
        .others(map)
        .build();
  }

  private String extractLast(Path path) {
    String[] split = path.toString().split("\\.");
    return split[split.length - 1];
  }

}
