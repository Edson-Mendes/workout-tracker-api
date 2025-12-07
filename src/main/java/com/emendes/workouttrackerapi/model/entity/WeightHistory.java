package com.emendes.workouttrackerapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa a tabela tb_weight_history do banco de dados.
 */
@Entity
@Table(name = "tb_weight_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WeightHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private BigDecimal value;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  @ManyToOne
  private Exercise exercise;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    WeightHistory weightHistory = (WeightHistory) o;
    return id.equals(weightHistory.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "WeightHistory{" +
           "id=" + id +
           ", value=" + value +
           ", createdAt=" + createdAt +
           ", exercise=" + exercise +
           '}';
  }

}
