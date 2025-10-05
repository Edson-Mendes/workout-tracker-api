package com.emendes.workouttrackerapi.model.entity;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa a tabela tb_workout do banco de dados.
 */
@Entity
@Table(name = "tb_workout")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Workout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WorkoutStatus status;
  @ManyToOne(cascade = CascadeType.REMOVE)
  private User user;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    Workout workout = (Workout) o;
    return id.equals(workout.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Workout{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", status=" + status +
           '}';
  }
}
