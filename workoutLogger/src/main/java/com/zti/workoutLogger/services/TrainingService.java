package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.TrainingDto;

import java.util.List;

public interface TrainingService {

    TrainingDto createTraining(TrainingDto trainingDto, long workoutId);

    List<TrainingDto> getTrainingsByWorkoutId(long workoutId);

    TrainingDto getTrainingById(long workoutId, long trainingId);
}