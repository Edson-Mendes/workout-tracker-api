package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.CurrentUserComponent;
import com.emendes.workouttrackerapi.dao.WorkoutDao;
import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseSummaryResponse;
import com.emendes.workouttrackerapi.dto.response.WorkoutDetailsResponse;
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

import java.util.List;

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
  private ExerciseService exerciseService;

  /**
   * Registra um workout.
   *
   * @param workoutRegisterRequest com as informações do workout a ser salvo.
   * @return WorkoutSummaryResponse com as informações do workout salvo.
   */
  public WorkoutSummaryResponse registerWorkout(@Valid WorkoutRegisterRequest workoutRegisterRequest) {
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
   * Adiciona um exercício ao workout.
   *
   * @param workoutId               identificador do workout
   * @param exerciseRegisterRequest com as informações do exercise a ser adicionado.
   * @return {@code ExerciseSummaryResponse} com as informações do Exercise adicionado.
   */
  public ExerciseSummaryResponse addExercise(Long workoutId, @Valid ExerciseRegisterRequest exerciseRegisterRequest) {
    log.info("Attempt to add exercise to workout with id: {}", workoutId);

    Workout workout = findById(workoutId);
    ExerciseSummaryResponse exerciseResponse = exerciseService.register(workout, exerciseRegisterRequest);
    log.info("Exercise added successful");

    return exerciseResponse;
  }

  /**
   * Buscar Workout por id, devolve as informações do workout e todos os seus exercícios.
   *
   * @param workoutId identificador do workout.
   * @return {@code WorkoutDetailsResponse} com as informações detalhadas do workout encontrado.
   */
  public WorkoutDetailsResponse findWorkoutById(Long workoutId) {
    Workout workout = findById(workoutId);
    List<ExerciseSummaryResponse> exercises = exerciseService.fetchExercisesByWorkoutId(workoutId);

    WorkoutDetailsResponse workoutDetailsResponse = workoutMapper.toWorkoutDetailsResponse(workout, exercises);
    log.info("Exercises found successful for workout with id: {}", workoutId);

    return workoutDetailsResponse;
  }

  /**
   * Busca workout por id.
   *
   * @param workoutId identificador do Workout a ser buscado.
   * @return Workout encontrado para o dado workoutId.
   * @throws WebApplicationException caso não seja encontrado Workout para o dado workoutId.
   */
  private Workout findById(Long workoutId) {
    log.info("Attempt to find workout with id {}", workoutId);
    if (workoutId == null)
      throw new WebApplicationException("workoutId must not be null", Status.INTERNAL_SERVER_ERROR);

    Long userId = currentUserComponent.getCurrentUser().getId();
    return workoutDao.findByIdAndStatusAndUserId(workoutId, WorkoutStatus.ONGOING, userId)
        .orElseThrow(() -> new WebApplicationException("workout not found", Status.NOT_FOUND));
  }

}
