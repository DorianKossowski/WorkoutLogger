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
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Override
    public void editSets(List<ModelSetDto> sets, long trainingId, long exerciseId) {
        Training training = getTraining(trainingId);
        Exercise exercise = getExercise(exerciseId);

        List<ModelSet> modelSets =
                modelSetRepository.findAllById(sets.stream().map(ModelSetDto::getId).collect(Collectors.toSet()));

        sets.forEach(modelSetDto -> {
                    if (modelSetDto.getId() < 0) {
                        saveSet(modelSetDto, training, exercise);
                    } else {
                        ModelSet setToUpdate = modelSets.stream()
                                .filter(modelSet -> modelSet.getId() == modelSetDto.getId())
                                .collect(toSingleton());
                        updateSet(setToUpdate, modelSetDto);
                    }
                }
        );

        Set<Long> trainingAllSetsId = training.getSets().stream()
                .map(ModelSet::getId)
                .collect(Collectors.toSet());

        Set<Long> trainingExerciseSetsId = exercise.getSets().stream()
                .map(ModelSet::getId)
                .filter(trainingAllSetsId::contains)
                .collect(Collectors.toSet());

        deleteSets(sets, trainingExerciseSetsId);
    }

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    private void deleteSets(List<ModelSetDto> sets, Set<Long> existingSetsId) {
        Set<Long> newSetsId = sets.stream()
                .map(ModelSetDto::getId)
                .collect(Collectors.toSet());
        existingSetsId.forEach(existingId -> {
            if (!newSetsId.contains(existingId)) {
                deleteSet(existingId);
            }
        });
    }

    private void updateSet(ModelSet setToUpdate, ModelSetDto modelSetDto) {
        long setDtoId = modelSetDto.getId();
        setToUpdate.setReps(modelSetDto.getReps());
        setToUpdate.setWeight(modelSetDto.getWeight());
        modelSetRepository.save(setToUpdate);
        logger.debug(String.format("Set with id %s correctly updated", setDtoId));
    }

    private void deleteSet(long id) {
        modelSetRepository.deleteById(id);
        logger.debug(String.format("Set with id %s deleted correctly", id));
    }
}