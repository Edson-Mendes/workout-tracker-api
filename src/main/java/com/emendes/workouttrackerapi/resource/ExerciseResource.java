package com.emendes.workouttrackerapi.resource;

import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.service.ExerciseService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

import static com.emendes.workouttrackerapi.service.RoleService.USER_ROLE_NAME;

/**
 * Classe resource para lidar com as requisições para /workouts/{workoutId}/exercises.
 */
@Path("/workouts/{workoutId}/exercises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor_ = @Inject)
public class ExerciseResource {

  private ExerciseService exerciseService;

  @POST
  @RolesAllowed(USER_ROLE_NAME)
  public Response register(@PathParam("workoutId") Long workoutId, ExerciseRegisterRequest exerciseRegisterRequest) {
    return Response
        .status(Status.CREATED)
        .entity(exerciseService.register(workoutId, exerciseRegisterRequest))
        .build();
  }

}
