package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto, long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new InvalidArgumentException("Workout doesn't exists"));
        trainingRepository.save(new Training(workout));
        return trainingDto;
    }
}