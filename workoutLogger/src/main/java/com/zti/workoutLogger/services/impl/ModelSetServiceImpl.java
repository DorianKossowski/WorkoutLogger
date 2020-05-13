package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.ModelSet;
import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.dto.ModelSetDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.repositories.ModelSetRepository;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.services.ModelSetService;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelSetServiceImpl implements ModelSetService {
    private final static Logger logger = LoggerFactory.getLogger(ModelSetServiceImpl.class);

    @Autowired
    private ModelSetRepository modelSetRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public List<ModelSetDto> createSets(List<ModelSetDto> modelSetDtos, long trainingId, long exerciseId) {
        Training training = getTraining(trainingId);
        Exercise exercise = getExercise(exerciseId);
        modelSetDtos.forEach(modelSetDto -> saveSet(modelSetDto, training, exercise));
        return modelSetDtos;
    }

    private Exercise getExercise(long exerciseId) {
        return exerciseRepository.findById(exerciseId).orElseThrow(() -> new InvalidArgumentException(
                String.format("Exercise with id %s doesn't exist", exerciseId)));
    }

    private Training getTraining(long trainingId) {
        return trainingRepository.findById(trainingId).orElseThrow(() -> new InvalidArgumentException(
                String.format("Training with id %s doesn't exist", trainingId)));
    }

    @Override
    public ModelSetDto createSet(ModelSetDto modelSetDto, long trainingId, long exerciseId) {
        Training training = getTraining(trainingId);
        Exercise exercise = getExercise(exerciseId);
        saveSet(modelSetDto, training, exercise);
        return modelSetDto;
    }

    private void saveSet(ModelSetDto modelSetDto, Training training, Exercise exercise) {
        long newSetId = modelSetRepository.save(new ModelSet(training, exercise, modelSetDto)).getId();
        logger.debug(String.format("Set with id %s correctly created", newSetId));
        modelSetDto.setId(newSetId);
    }
}