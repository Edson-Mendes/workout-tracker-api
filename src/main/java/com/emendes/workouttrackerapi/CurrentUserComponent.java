package com.emendes.workouttrackerapi;

import com.emendes.workouttrackerapi.model.entity.User;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class CurrentUserComponent {

  private SecurityIdentity securityIdentity;

  /**
   * Busca o usuário autenticado.
   *
   * @return usuário autenticado atualmente.
   * @throws WebApplicationException com status UNAUTHORIZED caso não tenha usuário autenticado.
   */
  public User getCurrentUser() {
    if (securityIdentity.isAnonymous())
      throw new WebApplicationException("must be authenticated to access this resource", Response.Status.UNAUTHORIZED);
    return securityIdentity.getAttribute("user");
  }

}
