package com.emendes.workouttrackerapi.security;

import com.emendes.workouttrackerapi.dao.UserDao;
import com.emendes.workouttrackerapi.model.entity.Role;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.stream.Collectors;

/**
 * IdentityProvider customizado para buscar o usuário no banco de dados por username (email), e inseri-lo
 * no SecurityIndentiy.
 */
@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class DatabaseIdentityProvider implements IdentityProvider<UsernamePasswordAuthenticationRequest> {

  private UserDao userDao;

  @Override
  public Class<UsernamePasswordAuthenticationRequest> getRequestType() {
    return UsernamePasswordAuthenticationRequest.class;
  }

  @Override
  public Uni<SecurityIdentity> authenticate(UsernamePasswordAuthenticationRequest request, AuthenticationRequestContext context) {
    String username = request.getUsername();
    String password = String.copyValueOf(request.getPassword().getPassword());

    return Uni.createFrom().item(() ->
        userDao.findByEmail(username)
            .map(user -> {
              if (BcryptUtil.matches(password, user.getPassword())) {
                Principal principal = user::getEmail;

                return QuarkusSecurityIdentity.builder()
                    .setPrincipal(principal)
                    .addRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .build();
              } else {
                throw new AuthenticationFailedException("invalid username or password");
              }
            })
            .orElseThrow(() -> new AuthenticationFailedException("invalid username or password"))
    );
  }

}
