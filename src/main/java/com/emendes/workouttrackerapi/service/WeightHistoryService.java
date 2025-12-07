package com.emendes.workouttrackerapi.service;

import com.emendes.workouttrackerapi.dao.WeightHistoryDao;
import com.emendes.workouttrackerapi.mapper.WeightHistoryMapper;
import com.emendes.workouttrackerapi.model.entity.Exercise;
import com.emendes.workouttrackerapi.model.entity.WeightHistory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
public class WeightHistoryService {

  private WeightHistoryDao weightHistoryDao;
  private WeightHistoryMapper weightHistoryMapper;

  public void save(Exercise exercise) {
    log.info("attempt to add WeightHistory for exercise with id: {}", exercise.getId());
    WeightHistory weightHistory = weightHistoryMapper.toWeightHistory(exercise);
    weightHistoryDao.save(weightHistory);
    log.info("WeightHistory save successful");
  }

}
