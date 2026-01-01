package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.Exercise;
import com.emendes.workouttrackerapi.model.entity.WeightHistory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * DAO responsável pela interação com o recurso WeightHistory no banco de dados.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class WeightHistoryDao {

  private EntityManager entityManager;

  /**
   * Salva um WeightHistory no banco de dados.
   */
  @Transactional
  public void save(WeightHistory weightHistory) {
    entityManager.persist(weightHistory);
  }

  /**
   * Busca WeightHistory por exerciseId.
   *
   * @param exerciseId indetificador do Exercise.
   * @return {@code List<WeightHistory>} lista de WeightHistory.
   */
  public List<WeightHistory> fetchByExerciseId(Long exerciseId) {
    return entityManager.createQuery("""
            SELECT wh.id, wh.value, wh.createdAt FROM WeightHistory wh
              WHERE wh.exercise.id = :exerciseId
            """, WeightHistory.class)
        .setParameter("exerciseId", exerciseId)
        .getResultList();
  }

  /**
   * Deleta WeightHistory por id.
   *
   * @param exercise        Exercise que contém o WeightHistory a ser deletado.
   * @param weightHistoryId identificador do WeightHistory a ser deletado.
   * @return número de entidades deletadas.
   */
  @Transactional
  public int delete(Exercise exercise, Long weightHistoryId) {
    return entityManager.createQuery("""
            DELETE FROM WeightHistory wh
              WHERE wh.exercise.id = :exerciseId
              AND wh.id = :weightHistoryId
            """)
        .setParameter("exerciseId", exercise.getId())
        .setParameter("weightHistoryId", weightHistoryId)
        .executeUpdate();
  }

}
