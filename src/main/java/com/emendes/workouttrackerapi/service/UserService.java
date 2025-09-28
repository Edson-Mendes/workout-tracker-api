package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.UserDao;
import com.emendes.workouttrackerapi.dto.request.UserRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.UserDetailsResponse;
import com.emendes.workouttrackerapi.mapper.UserMapper;
import com.emendes.workouttrackerapi.model.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsável pelas regras de negócio sobre User.
 */
@ApplicationScoped
@Slf4j
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserService {

  private UserDao userDao;
  private UserMapper userMapper;
  private RoleService roleService;

  public UserDetailsResponse register(@Valid UserRegisterRequest userRegisterRequest) {
    log.info("Attempt to register user");
    if (userRegisterRequest.password().equals(userRegisterRequest.confirmPassword())) {
      User user = userMapper.toUser(userRegisterRequest);
      user.setPassword(BcryptUtil.bcryptHash(userRegisterRequest.password()));
      user.addRole(roleService.getUserRole());

      user = userDao.register(user);
      log.info("user with id {} registered successfully", user.getId());

      return userMapper.toUserDetailsResponse(user);
    }
    throw new WebApplicationException("Passwords do not match", Status.BAD_REQUEST);
  }

}
