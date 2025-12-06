package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.Exercise;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * DAO responsável pela interação com o recurso Exercise no banco de dados.
 */
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class ExerciseDao {

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
   * Busca Exercises por workoutId.
   */
  public List<Exercise> fetchExercisesByWorkoutId(Long workoutId) {
    return entityManager.createQuery("""
            SELECT e.id, e.name, e.sets, e.weight FROM Exercise e
              WHERE e.workout.id = :workoutId
            """, Exercise.class)
        .setParameter("workoutId", workoutId)
        .getResultList();
  }
}
