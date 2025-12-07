package com.emendes.workouttrackerapi.model.entity;

import com.emendes.workouttrackerapi.model.WorkoutStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Exercise> exercises;

  public Workout(Long id, String name, WorkoutStatus status, Long userId) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.user = new User.UserBuilder().id(userId).build();
  }

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
