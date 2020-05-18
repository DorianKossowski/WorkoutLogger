package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.ModelSet;
import com.zti.workoutLogger.models.dto.ModelSetDto;

import java.util.List;
import java.util.Set;

public interface ModelSetService {

    List<ModelSetDto> createSets(List<ModelSetDto> modelSetDtos, long trainingId, long exerciseId);

    ModelSetDto createSet(ModelSetDto modelSetDto, long trainingId, long exerciseId);

    void editSets(List<ModelSetDto> sets, long trainingId, long exerciseId);

    void deleteSets(Set<ModelSet> sets);
}