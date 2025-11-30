package com.emendes.workouttrackerapi.model.entity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa a tabela tb_user do banco de dados.
 */
@Entity
@Table(name = "tb_user")
@UserDefinition
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Username
  @Column(unique = true, nullable = false)
  private String email;
  @Password
  @Column(nullable = false)
  private String password;
  @Roles
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "tb_user_roles",
      joinColumns = @JoinColumn(name = "user_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
  )
  private Set<Role> roles;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;
    return id.equals(user.id) && email.equals(user.email);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + email.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
           "id=" + id +
           ", email='" + email + '\'' +
           '}';
  }

  public void addRole(Role role) {
    if (roles == null)
      roles = new HashSet<>();
    roles.add(role);
  }

}
