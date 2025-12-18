package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.ExerciseDao;
import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseDetailsResponse;
import com.emendes.workouttrackerapi.dto.response.ExerciseSummaryResponse;
import com.emendes.workouttrackerapi.dto.response.WeightHistoryResponse;
import com.emendes.workouttrackerapi.mapper.ExerciseMapper;
import com.emendes.workouttrackerapi.model.entity.Exercise;
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
 * Service responsável pelas regras de negócio sobre Exercise.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class ExerciseService {

  private ExerciseDao exerciseDao;
  private ExerciseMapper exerciseMapper;
  private WeightHistoryService weightHistoryService;

  /**
   * Registra um exercise no sistema.
   *
   * @param workout                 Workout que receberá o exercise.
   * @param exerciseRegisterRequest objeto contendo as informações do exercise.
   * @return ExerciseSummaryResponse com as informações do Exercise registrado.
   */
  public ExerciseSummaryResponse register(Workout workout, ExerciseRegisterRequest exerciseRegisterRequest) {
    log.info("Attempt to register exercise");
    if (workout == null) {
      log.info("workout must not be null");
      throw new WebApplicationException("workout must not be null", Status.INTERNAL_SERVER_ERROR);
    }

    Exercise exercise = exerciseMapper.toExercise(exerciseRegisterRequest);
    exercise.setWorkout(workout);
    exercise = exerciseDao.register(exercise);
    weightHistoryService.save(exercise);

    log.info("Exercise registered successful with id {}", exercise.getId());
    return exerciseMapper.toExerciseSummaryResponse(exercise);
  }

  /**
   * Buscar exercises por workoutId.
   *
   * @param workoutId identificador do workout que possui os exercises.
   * @return {@code List<ExerciseSummaryResponse>} lista dos Exercises relacionados ao workout.
   */
  public List<ExerciseSummaryResponse> fetchExercisesByWorkoutId(Long workoutId) {
    log.info("Attempt to fetch exercises by workoutId: {}", workoutId);

    return exerciseDao.fetchExercisesByWorkoutId(workoutId)
        .stream()
        .map(exerciseMapper::toExerciseSummaryResponse)
        .toList();
  }

  /**
   * Buscar Exercise por exerciseId, workoutId e userId.
   *
   * @param exerciseId identificador do Exercise a ser buscado.
   * @param workoutId  identificador do Workout relacionado com o Exercise.
   * @param userId     idenficador do User relacionado com o Workout.
   * @return ExerciseDetailsResponse contendo informações detalhadas do Exercise encontrado.
   */
  public ExerciseDetailsResponse findByExerciseIdAndWorkoutIdAndUserId(Long exerciseId, Long workoutId, Long userId) {
    log.info("Attempt to find Exercise by exerciseId, workoutId and userId");
    Exercise exercise = findExerciseBy(exerciseId, workoutId, userId);
    List<WeightHistoryResponse> weightHistoryResponseList = weightHistoryService.fetchWeightHistoryByExerciseId(exerciseId);

    return ExerciseMapper.toExerciseDetailsResponse(exercise, weightHistoryResponseList);
  }

  /**
   * Atualizar Exercise por exerciseId, workoutId e userId.
   *
   * @param exerciseId              identificador do Exercise a ser atualizado.
   * @param workoutId               identificador do Workout relacionado com o Exercise.
   * @param userId                  idenficador do User relacionado com o Workout.
   * @param exerciseRegisterRequest objeto contendo as informações do exercise.
   * @return ExerciseDetailsResponse contendo informações detalhadas do Exercise encontrado.
   */
  public ExerciseSummaryResponse updateExercise(Long exerciseId, Long workoutId, Long userId, @Valid ExerciseRegisterRequest exerciseRegisterRequest) {
    log.info("Attempt to update Exercise by ID: {}", exerciseId);
    Exercise exercise = findExerciseBy(exerciseId, workoutId, userId);
    exerciseMapper.merge(exercise, exerciseRegisterRequest);

    exerciseDao.update(exercise);
    weightHistoryService.save(exercise);
    log.info("Exercise updated successful");
    return exerciseMapper.toExerciseSummaryResponse(exercise);
  }

  /**
   * Busca Exercise na camada DAO.
   */
  private Exercise findExerciseBy(Long exerciseId, Long workoutId, Long userId) {
    return exerciseDao.findExerciseById(exerciseId, workoutId, userId)
        .orElseThrow(() -> new WebApplicationException("exercise not found", Status.NOT_FOUND));
  }

}
