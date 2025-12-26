package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.Exercise;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * DAO responsável pela interação com o recurso Exercise no banco de dados.
 */
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class ExerciseDao {

  public static final String WORKOUT_ID_PARAM = "workoutId";
  private EntityManager entityManager;

  /**
   * Registra um Exercise na base de dados.
   */
  @Transactional
  public Exercise register(Exercise exercise) {
    entityManager.persist(exercise);

    return exercise;
  }

  /**
   * Atualiza um Exercise na base de dados.
   */
  @Transactional
  public Exercise update(Exercise exercise) {
    return entityManager.merge(exercise);
  }

  /**
   * Busca Exercises por workoutId.
   */
  public List<Exercise> fetchExercisesByWorkoutId(Long workoutId) {
    return entityManager.createQuery("""
            SELECT e.id, e.name, e.sets, e.weight FROM Exercise e
              WHERE e.workout.id = :workoutId
            """, Exercise.class)
        .setParameter(WORKOUT_ID_PARAM, workoutId)
        .getResultList();
  }

  /**
   * Busca Exercise por exerciseId, workoutId e userId.
   */
  public Optional<Exercise> findExerciseById(Long exerciseId, Long workoutId, Long userId) {
    Exercise exercise = entityManager.createQuery("""
            SELECT e.id, e.name, e.sets, e.weight, e.workout.id FROM Exercise e
              WHERE e.id = :exerciseId
              AND e.workout.id = :workoutId
              AND e.workout.user.id = :userId
            """, Exercise.class)
        .setParameter("exerciseId", exerciseId)
        .setParameter(WORKOUT_ID_PARAM, workoutId)
        .setParameter("userId", userId)
        .getSingleResultOrNull();

    return Optional.ofNullable(exercise);
  }

  /**
   * Busca Exercises por exerciseId e workoutId.
   */
  public Optional<Exercise> findExerciseById(Long exerciseId, Long workoutId) {
    Exercise exercise = entityManager.createQuery("""
            SELECT e.id, e.name, e.sets, e.weight, e.workout.id FROM Exercise e
              WHERE e.id = :exerciseId
              AND e.workout.id = :workoutId
            """, Exercise.class)
        .setParameter("exerciseId", exerciseId)
        .setParameter(WORKOUT_ID_PARAM, workoutId)
        .getSingleResultOrNull();

    return Optional.ofNullable(exercise);
  }

}
