package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.WeightHistoryDao;
import com.emendes.workouttrackerapi.dto.response.WeightHistoryResponse;
import com.emendes.workouttrackerapi.mapper.WeightHistoryMapper;
import com.emendes.workouttrackerapi.model.entity.Exercise;
import com.emendes.workouttrackerapi.model.entity.WeightHistory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class WeightHistoryService {

  private WeightHistoryDao weightHistoryDao;
  private WeightHistoryMapper weightHistoryMapper;

  /**
   * Salva um histórico de carga com base no Exercise.
   *
   * @param exercise Exercise ao qual o histórico estará relacionado.
   */
  public void save(Exercise exercise) {
    log.info("attempt to add WeightHistory for exercise with id: {}", exercise.getId());
    WeightHistory weightHistory = weightHistoryMapper.toWeightHistory(exercise);
    weightHistoryDao.save(weightHistory);
    log.info("WeightHistory save successful");
  }

  /**
   * Busca todos os WeightHistory para um dado exerciseId.
   *
   * @param exerciseId identificador do Exercise que contém os WeightHistory.
   * @return {@code List<WeightHistoryResponse>} lista dos WeightHistoryResponse encontrados.
   */
  public List<WeightHistoryResponse> fetchWeightHistoryByExerciseId(Long exerciseId) {
    log.info("Attempt to fetch WeightHistory by exerciseId");
    if (exerciseId == null)
      throw new WebApplicationException("exerciseId must not be null", Response.Status.INTERNAL_SERVER_ERROR);

    return weightHistoryDao.fetchByExerciseId(exerciseId)
        .stream()
        .map(weightHistoryMapper::toWeightHistoryResponse)
        .toList();
  }

  /**
   * Deletar WeightHistory por ID de um Exercise.
   *
   * @param exercise        Exercise que contém o WeightHistory.
   * @param weightHistoryId identificador do WeightHistory a ser deletado.
   */
  public void deleteWeightHistory(Exercise exercise, Long weightHistoryId) {
    int totalEntitiesDeleted = weightHistoryDao.delete(exercise, weightHistoryId);
    if (totalEntitiesDeleted == 0)
      throw new WebApplicationException("WeightHistory not found", Response.Status.NOT_FOUND);
  }

}
