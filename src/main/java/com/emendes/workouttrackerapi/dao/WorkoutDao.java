package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.model.entity.Workout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * DAO responsável pela interação com o recurso Workout no banco de dados.
 */
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class WorkoutDao {

  private EntityManager entityManager;

  /**
   * Registra um workout na base de dados.
   */
  @Transactional
  public Workout register(Workout workout) {
    entityManager.persist(workout);

    return workout;
  }

  /**
   * Atualiza um Workout na base de dados.
   */
  @Transactional
  public Workout update(Workout workout) {
    return entityManager.merge(workout);
  }

  /**
   * Busca Wokrout por id, status e userId.
   *
   * @param workoutId identificador do workout.
   * @param status    status do workout.
   * @param userId    identificador do usuário dono do workout.
   * @return {@code Optional<Workout>} contendo o workout encontrado, empty caso contrário.
   */
  public Optional<Workout> findByIdAndStatusAndUserId(Long workoutId, WorkoutStatus status, Long userId) {
    Workout workoutFound = entityManager.createQuery("""
            SELECT w.id, w.name, w.status, w.user.id FROM Workout w
              WHERE w.id = :workoutId
              AND w.status = :status
              AND w.user.id = :userId
            """, Workout.class)
        .setParameter("workoutId", workoutId)
        .setParameter("status", status)
        .setParameter("userId", userId)
        .getSingleResultOrNull();

    return Optional.ofNullable(workoutFound);
  }

  /**
   * Busca Wokrout por id e userId.
   *
   * @param workoutId identificador do workout.
   * @param userId    identificador do usuário dono do workout.
   * @return {@code Optional<Workout>} contendo o workout encontrado, empty caso contrário.
   */
  public Optional<Workout> findByIdAndUserId(Long workoutId, Long userId) {
    Workout workoutFound = entityManager.createQuery("""
            SELECT w.id, w.name, w.status, w.user.id FROM Workout w
              WHERE w.id = :workoutId
              AND w.user.id = :userId
            """, Workout.class)
        .setParameter("workoutId", workoutId)
        .setParameter("userId", userId)
        .getSingleResultOrNull();

    return Optional.ofNullable(workoutFound);
  }

}
