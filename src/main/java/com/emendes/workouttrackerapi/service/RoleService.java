package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.model.entity.Role;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsável pelas regras de negócio sobre Role.
 */
@ApplicationScoped
@Slf4j
public class RoleService {

  private static final String USER_ROLE_NAME = "user";
  private static final Integer USER_ROLE_ID = 1;

  /**
   * Busca a role "user".
   */
  public Role getUserRole() {
    return Role.builder()
        .id(USER_ROLE_ID)
        .name(USER_ROLE_NAME)
        .build();
  }

}
