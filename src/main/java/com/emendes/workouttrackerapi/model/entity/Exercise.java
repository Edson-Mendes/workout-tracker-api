package com.emendes.workouttrackerapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
  @ManyToOne(fetch = FetchType.LAZY)
  private Workout workout;
  @OneToMany(mappedBy = "exercise", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<WeightHistory> weightHistories;

  public Exercise(Long id, String name, int sets, BigDecimal weight, Long workoutId) {
    this(id, name, sets, weight);
    this.workout = new Workout.WorkoutBuilder()
        .id(workoutId)
        .build();
  }

  public Exercise(Long id, String name, int sets, BigDecimal weight) {
    this.id = id;
    this.name = name;
    this.sets = sets;
    this.weight = weight;
  }

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
