package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.CurrentUserComponent;
import com.emendes.workouttrackerapi.dao.WorkoutDao;
import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.WorkoutSummaryResponse;
import com.emendes.workouttrackerapi.mapper.WorkoutMapper;
import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.model.entity.User;
import com.emendes.workouttrackerapi.model.entity.Workout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
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
  private CurrentUserComponent currentUserComponent;

  /**
   * Registra um workout.
   *
   * @param workoutRegisterRequest com as informações do workout a ser salvo.
   * @return WorkoutSummaryResponse com as informações do worokout salvo.
   */
  public WorkoutSummaryResponse register(@Valid WorkoutRegisterRequest workoutRegisterRequest) {
    log.info("Attempt to register workout");
    User currentUser = currentUserComponent.getCurrentUser();
    Workout workout = workoutMapper.toWorkout(workoutRegisterRequest);
    workout.setStatus(WorkoutStatus.ONGOING);
    workout.setUser(currentUser);
    workout = workoutDao.register(workout);
    log.info("Workout registered successful");

    return workoutMapper.toWorkoutSummaryResponse(workout);
  }

  /**
   * Busca workout por id.
   *
   * @param workoutId identificador do Workout a ser buscado.
   * @return Workout encontrado para o dado workoutId.
   * @throws WebApplicationException caso não seja encontrado Workout para o dado workoutId.
   */
  protected Workout findById(Long workoutId) {
    log.info("Attempt to find workout with id {}", workoutId);

    Long userId = currentUserComponent.getCurrentUser().getId();
    return workoutDao.findByIdAndStatusAndUserId(workoutId, WorkoutStatus.ONGOING, userId)
        .orElseThrow(() -> new WebApplicationException("workout not found", Status.NOT_FOUND));
  }

}
