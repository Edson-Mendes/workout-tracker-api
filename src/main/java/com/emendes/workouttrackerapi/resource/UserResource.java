package com.emendes.workouttrackerapi.resource;

import com.emendes.workouttrackerapi.dto.request.UserRegisterRequest;
import com.emendes.workouttrackerapi.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

/**
 * Classe resource para lidar com as requisições para /users.
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserResource {

  private UserService userService;

  @POST
  @PermitAll
  public Response register(UserRegisterRequest userRegisterRequest) {
    return Response
        .status(Status.CREATED)
        .entity(userService.register(userRegisterRequest))
        .build();
  }

}
