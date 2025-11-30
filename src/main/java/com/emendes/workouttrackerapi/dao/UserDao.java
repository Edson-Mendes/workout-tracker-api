package com.emendes.workouttrackerapi.dao;

import com.emendes.workouttrackerapi.model.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * DAO responsável pela interação com o recurso User no banco de dados.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserDao {

  private EntityManager entityManager;

  /**
   * Registra um usuário na base de dados.
   */
  @Transactional
  public User register(User user) {
    entityManager.persist(user);

    return user;
  }

  /**
   * Busca usuário por email.
   *
   * @param email parâmetro o qual o usuário será buscado.
   * @return {@code Optional<User>} contendo o usuário encontrado, ou vazio caso nada seja encontrado.
   */
  @Transactional
  public Optional<User> findByEmail(String email) {
    log.info("Buscando usuário por email: {}", email);
    User userFound = entityManager.createQuery("""
            SELECT u FROM User u WHERE u.email = :email
            """, User.class).setParameter("email", email)
        .getSingleResultOrNull();

    return Optional.ofNullable(userFound);
  }

}
