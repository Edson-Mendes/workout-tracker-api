package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.Workout;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

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

}
