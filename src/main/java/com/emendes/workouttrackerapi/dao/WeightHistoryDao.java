package com.emendes.workouttrackerapi.dao;

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
}
