package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * DAO responsável pela interação com o recurso User no banco de dados.
 */
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserDao {

  private EntityManager entityManager;

  @Transactional
  public User register(User user) {
    entityManager.persist(user);

    return user;
  }

}
