package com.emendes.workouttrackerapi.model.entity;

import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa a tabela tb_role do banco de dados.
 */
@Entity
@Table(name = "tb_role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @RolesValue
  private String name;

}
