package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto, long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new InvalidArgumentException("Workout doesn't exists"));
        long id = trainingRepository.save(new Training(workout)).getId();
        logger.debug(String.format("Training with id %s correctly created", id));
        return trainingDto;
    }

    @Override
    public List<TrainingDto> getTrainingsByWorkoutId(long workoutId) {
        return trainingRepository.findAllByWorkoutId(workoutId).stream()
                .map(TrainingDto::new)
                .collect(Collectors.toList());
    }

}