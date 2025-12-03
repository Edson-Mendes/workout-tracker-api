package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.ExerciseDao;
import com.emendes.workouttrackerapi.dto.request.ExerciseRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.ExerciseSummaryResponse;
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

/**
 * Service responsável pelas regras de negócio sobre Exercise.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class ExerciseService {

  private ExerciseDao exerciseDao;
  private ExerciseMapper exerciseMapper;
  private WorkoutService workoutService;

  /**
   * Registra um exercise no sistema.
   *
   * @param workoutId               identificador do Workout ao qual o Exercise pertence.
   * @param exerciseRegisterRequest objeto contendo as informações do exercise.
   * @return ExerciseSummaryResponse com as informações do Exercise registrado.
   */
  public ExerciseSummaryResponse register(Long workoutId, @Valid ExerciseRegisterRequest exerciseRegisterRequest) {
    log.info("Attempt to register exercise");
    if (workoutId == null) throw new WebApplicationException("something went wrong", Status.BAD_REQUEST);

    Workout workout = workoutService.findById(workoutId);
    Exercise exercise = exerciseMapper.toExercise(exerciseRegisterRequest);
    exercise.setWorkout(workout);
    exercise = exerciseDao.register(exercise);

    log.info("Exercise registered successful with id {}", exercise.getId());
    return exerciseMapper.toExerciseSummaryResponse(exercise);
  }

}
