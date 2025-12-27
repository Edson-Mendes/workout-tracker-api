package com.emendes.workouttrackerapi.resource;

import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.request.WorkoutUpdateRequest;
import com.emendes.workouttrackerapi.service.WorkoutService;
import com.emendes.workouttrackerapi.validation.annotation.WorkoutStatusValidation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.constraints.PositiveOrZero;
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

  /**
   * Endpoint para registrar workout.
   */
  @POST
  @RolesAllowed({USER_ROLE_NAME})
  public Response registerWorkout(WorkoutRegisterRequest workoutRegisterRequest) {
    return Response
        .status(Response.Status.CREATED)
        .entity(workoutService.registerWorkout(workoutRegisterRequest))
        .build();
  }

  /**
   * Endpoint para buscar workouts.
   */
  @GET
  @RolesAllowed({USER_ROLE_NAME})
  public Response fetchWorkouts(
      @WorkoutStatusValidation @QueryParam("status") String status,
      @PositiveOrZero(message = "page must be positive or zero") @QueryParam("page") int page) {
    return Response
        .status(Response.Status.OK)
        .entity(workoutService.fetchWorkouts(status, page))
        .build();
  }

  /**
   * Endpoint para buscar Workout por ID.
   */
  @GET
  @Path("/{workoutId}")
  @RolesAllowed({USER_ROLE_NAME})
  public Response findWorkoutById(@PathParam("workoutId") Long workoutId) {
    return Response
        .status(Response.Status.OK)
        .entity(workoutService.findWorkoutById(workoutId))
        .build();
  }

  /**
   * Endpoint para atualizar Workout por ID.
   */
  @PUT
  @Path("/{workoutId}")
  @RolesAllowed({USER_ROLE_NAME})
  public Response updateWorkoutById(@PathParam("workoutId") Long workoutId, WorkoutUpdateRequest workoutUpdateRequest) {
    return Response
        .status(Response.Status.OK)
        .entity(workoutService.updateWorkoutById(workoutId, workoutUpdateRequest))
        .build();
  }

  /**
   * Enpoint para adicionar Exercise.
   */
  @POST
  @Path("/{workoutId}/exercises")
  @RolesAllowed({USER_ROLE_NAME})
  public Response addExercise(@PathParam("workoutId") Long workoutId, ExerciseRegisterRequest exerciseRegisterRequest) {
    return Response
        .status(Response.Status.CREATED)
        .entity(workoutService.addExercise(workoutId, exerciseRegisterRequest))
        .build();
  }

  /**
   * Endpoint para buscar Exercise por id.
   */
  @GET
  @Path("/{workoutId}/exercises/{exerciseId}")
  @RolesAllowed({USER_ROLE_NAME})
  public Response findExerciseById(@PathParam("workoutId") Long workoutId, @PathParam("exerciseId") Long exerciseId) {
    return Response
        .status(Response.Status.OK)
        .entity(workoutService.findExerciseById(workoutId, exerciseId))
        .build();
  }

  /**
   * Enpoint para atualizar Exercise.
   */
  @PUT
  @Path("/{workoutId}/exercises/{exerciseId}")
  @RolesAllowed({USER_ROLE_NAME})
  public Response updateExercise(
      @PathParam("workoutId") Long workoutId,
      @PathParam("exerciseId") Long exerciseId,
      ExerciseRegisterRequest exerciseRegisterRequest) {
    return Response
        .status(Response.Status.OK)
        .entity(workoutService.updateExercise(workoutId, exerciseId, exerciseRegisterRequest))
        .build();
  }

}
