package com.emendes.workouttrackerapi.model;

/**
 * Enum para indicar o status de um Workout.
 */
public enum WorkoutStatus {
  /**
   * Indica que o workout está em uso pelo usuário.
   */
  ONGOING,
  /**
   * Indica que o workout não está mais em uso pelo usuário.
   */
  FINISHED
}
