package com.emendes.workouttrackerapi.resource;

import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.service.WorkoutService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import static com.emendes.workouttrackerapi.service.RoleService.USER_ROLE_NAME;

/**
 * Classe resource para lidar com as requisições para /workouts.
 */
@Path("/workouts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor_ = @Inject)
public class WorkoutResource {

  private WorkoutService workoutService;

  @POST
  @RolesAllowed({USER_ROLE_NAME})
  public Response register(WorkoutRegisterRequest workoutRegisterRequest) {
    return Response
        .status(Response.Status.CREATED)
        .entity(workoutService.register(workoutRegisterRequest))
        .build();
  }

}
