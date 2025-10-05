package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.WorkoutDao;
import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.WorkoutSummaryResponse;
import com.emendes.workouttrackerapi.mapper.WorkoutMapper;
import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.model.entity.User;
import com.emendes.workouttrackerapi.model.entity.Workout;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsável pelas regras de negócio sobre Workout.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class WorkoutService {

  private WorkoutDao workoutDao;
  private WorkoutMapper workoutMapper;
  private SecurityIdentity securityIdentity;

  public WorkoutSummaryResponse register(@Valid WorkoutRegisterRequest workoutRegisterRequest) {
    log.info("Attempt to register workout");
    if (securityIdentity.isAnonymous())
      throw new WebApplicationException("must be authenticated to access this resource", Response.Status.UNAUTHORIZED);
    User currentUser = securityIdentity.getAttribute("user");
    Workout workout = workoutMapper.toWorkout(workoutRegisterRequest);
    workout.setStatus(WorkoutStatus.ONGOING);
    workout.setUser(currentUser);
    workout = workoutDao.register(workout);
    log.info("Workout registered successful");

    return workoutMapper.toWorkoutSummaryResponse(workout);
  }

}
