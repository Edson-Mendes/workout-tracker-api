package com.emendes.workouttrackerapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidade que representa a tabela tb_exercise do banco de dados.
 */
@Entity
@Table(name = "tb_exercise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Exercise {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private int sets;
  @Column(nullable = false)
  private BigDecimal weight;
  @ManyToOne(cascade = CascadeType.REMOVE)
  private Workout workout;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Exercise exercise = (Exercise) o;
    return id.equals(exercise.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Exercise{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", sets=" + sets +
           ", weight=" + weight +
           '}';
  }

}
