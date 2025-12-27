package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import com.emendes.workouttrackerapi.model.entity.Workout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
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

  /**
   * Busca paginada de Workouts por userId.
   *
   * @param userId identificador do usuário.
   * @param limit  quantidade limite de elementos a serem buscados.
   * @param offset quantidade de elementos a serem ignorados antes de contar para a busca.
   * @return {@code List<Workout>} lista com os Workouts encontrados.
   */
  public List<Workout> fetchByUserId(Long userId, int limit, int offset) {
    return entityManager.createQuery("""
            SELECT w.id, w.name, w.status, w.user.id FROM Workout w
              WHERE w.user.id = :userId
              ORDER by w.status DESC, w.id DESC
              LIMIT :limit
              OFFSET :offset
            """, Workout.class)
        .setParameter("userId", userId)
        .setParameter("limit", limit)
        .setParameter("offset", offset)
        .getResultList();
  }

  /**
   * Retorna a quantidade de Workouts por userId.
   *
   * @param userId identificador do usuário.
   * @return quantidade de recursos encontrado.
   */
  public long countByUserId(Long userId) {
    return entityManager.createQuery("""
            SELECT count(w.id) FROM Workout w
              WHERE w.user.id = :userId
            """, Long.class)
        .setParameter("userId", userId)
        .getSingleResult();
  }

  /**
   * Busca paginada de Workouts por userId e status.
   *
   * @param userId identificador do usuário.
   * @param status status do workout.
   * @param limit  quantidade limite de elementos a serem buscados.
   * @param offset quantidade de elementos a serem ignorados antes de contar para a busca.
   * @return {@code List<Workout>} lista com os Workouts encontrados.
   */
  public List<Workout> fetchByUserIdAndStatus(Long userId, WorkoutStatus status, int limit, int offset) {
    return entityManager.createQuery("""
            SELECT w.id, w.name, w.status, w.user.id FROM Workout w
              WHERE w.user.id = :userId
              AND w.status = :status
              ORDER by w.status DESC, w.id DESC
              LIMIT :limit
              OFFSET :offset
            """, Workout.class)
        .setParameter("userId", userId)
        .setParameter("status", status)
        .setParameter("limit", limit)
        .setParameter("offset", offset)
        .getResultList();
  }

  /**
   * Retorna a quantidade de Workouts por userId e status.
   *
   * @param userId identificador do usuário.
   * @param status status do Workout.
   * @return quantidade de recursos encontrado.
   */
  public long countByUserIdAndStatus(Long userId, WorkoutStatus status) {
    return entityManager.createQuery("""
            SELECT count(w.id) FROM Workout w
              WHERE w.user.id = :userId
              AND w.status = :status
            """, Long.class)
        .setParameter("userId", userId)
        .setParameter("status", status)
        .getSingleResult();
  }

}
