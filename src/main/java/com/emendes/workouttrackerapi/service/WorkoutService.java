package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.CurrentUserComponent;
import com.emendes.workouttrackerapi.dao.WorkoutDao;
import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.request.WorkoutRegisterRequest;
import com.emendes.workouttrackerapi.dto.request.WorkoutUpdateRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseDetailsResponse;
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
    if (workout.getStatus().equals(WorkoutStatus.FINISHED)) {
      throw new WebApplicationException("It is not possible to add exercises to a FINISHED workout.", Status.BAD_REQUEST);
    }
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
   * Atualiza Workout por id.
   *
   * @param workoutId            identificador do Workout a ser atualizado.
   * @param workoutUpdateRequest com as novas informações do Workout.
   * @return {@code WorkoutSummaryResponse} com informações resumidas do Workout.
   */
  public WorkoutSummaryResponse updateWorkoutById(Long workoutId, @Valid WorkoutUpdateRequest workoutUpdateRequest) {
    log.info("Attempt to update workout with id {}", workoutId);
    checkWorkoutId(workoutId);

    Workout workout = findById(workoutId);
    workoutMapper.merge(workoutUpdateRequest, workout);
    workout = workoutDao.update(workout);

    log.info("Workout updated successful");
    return workoutMapper.toWorkoutSummaryResponse(workout);
  }

  /**
   * Buscar Exercise por id.
   *
   * @param workoutId  identificador do Workout relacionado com o Exercise a ser buscado.
   * @param exerciseId identificador do Exercise a ser buscado.
   * @return ExerciseDetailsResponse contendo informações detalhadas do Exercise encontrado.
   */
  public ExerciseDetailsResponse findExerciseById(Long workoutId, Long exerciseId) {
    log.info("Attempt to find Exercise by ID");
    checkWorkoutId(workoutId);
    checkExerciseId(exerciseId);

    Long userId = currentUserComponent.getCurrentUser().getId();
    ExerciseDetailsResponse exerciseDetailsResponse = exerciseService.findByExerciseIdAndWorkoutIdAndUserId(exerciseId, workoutId, userId);
    log.info("Exercise found successful with id: {}", exerciseId);

    return exerciseDetailsResponse;
  }

  /**
   * Atualizar Exercise por id.
   *
   * @param workoutId               identificador do Workout relacionado com o Exercise a ser atualizado.
   * @param exerciseId              identificador do Exercise a ser atualizado.
   * @param exerciseRegisterRequest com as informações a serem atualizadas do Exercise.
   * @return {@code ExerciseSummaryResponse} Contendo informações resumidas do Exercise.
   */
  public ExerciseSummaryResponse updateExercise(Long workoutId, Long exerciseId, @Valid ExerciseRegisterRequest exerciseRegisterRequest) {
    log.info("Attempt to update Exercise by ID");
    checkWorkoutId(workoutId);
    checkExerciseId(exerciseId);

    Workout workout = findById(workoutId);
    if (workout.getStatus().equals(WorkoutStatus.FINISHED)) {
      throw new WebApplicationException("It is not possible to update exercise in a FINISHED workout.", Status.BAD_REQUEST);
    }

    return exerciseService.updateExercise(exerciseId, workout, exerciseRegisterRequest);
  }

  /**
   * Busca workout por id e userId.
   *
   * @param workoutId identificador do Workout a ser buscado.
   * @return Workout encontrado para o dado workoutId.
   * @throws WebApplicationException caso não seja encontrado Workout para o dado workoutId.
   */
  private Workout findById(Long workoutId) {
    log.info("Attempt to search workout with id {}", workoutId);
    checkWorkoutId(workoutId);

    Long userId = currentUserComponent.getCurrentUser().getId();
    return workoutDao.findByIdAndUserId(workoutId, userId)
        .orElseThrow(() -> new WebApplicationException("workout not found", Status.NOT_FOUND));
  }

  /**
   * Verifica se o workoutId informado é nulo.
   *
   * @param workoutId identificador do workout.
   * @throws WebApplicationException caso workoutId seja nulo.
   */
  private void checkWorkoutId(Long workoutId) {
    if (workoutId == null)
      throw new WebApplicationException("workoutId must not be null", Status.INTERNAL_SERVER_ERROR);
  }

  /**
   * Verifica se o exerciseId informado é nulo.
   *
   * @param exerciseId identificador do workout.
   * @throws WebApplicationException caso exerciseId seja nulo.
   */
  private void checkExerciseId(Long exerciseId) {
    if (exerciseId == null)
      throw new WebApplicationException("exerciseId must not be null", Status.INTERNAL_SERVER_ERROR);
  }

}
