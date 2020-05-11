package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.TrainingDto;

public interface TrainingService {

    TrainingDto createTraining(TrainingDto trainingDto, long workoutId);
}