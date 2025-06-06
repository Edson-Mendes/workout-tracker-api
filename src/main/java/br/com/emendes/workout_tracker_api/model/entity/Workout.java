package br.com.emendes.workout_tracker_api.model.entity;

import br.com.emendes.workout_tracker_api.model.WorkoutStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Classe que representa a entidade Workout do banco de dados.
 */
@Entity
@Table(name = "tb_workout")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private Long id;
  @Column(name = "name", nullable = false, length = 100)
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private WorkoutStatus status;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

}
