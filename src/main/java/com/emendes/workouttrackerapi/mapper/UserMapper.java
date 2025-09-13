package com.emendes.workouttrackerapi.mapper;

import com.emendes.workouttrackerapi.dto.request.UserRegisterRequest;
import com.emendes.workouttrackerapi.dto.response.UserDetailsResponse;
import com.emendes.workouttrackerapi.model.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper responsável pelo mapeamento de classes relacionadas a User.
 */
@ApplicationScoped
public class UserMapper {

  public User toUser(UserRegisterRequest userRegisterRequest) {
    if (userRegisterRequest == null)
      throw new IllegalArgumentException("userDetailsRequest must not be null");
    return User.builder()
        .email(userRegisterRequest.email())
        .build();
  }

  public UserDetailsResponse toUserDetailsResponse(User user) {
    if (user == null)
      throw new IllegalArgumentException("user must not be null");
    return UserDetailsResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .build();
  }

}
