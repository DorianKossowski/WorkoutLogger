package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.ModelSetService;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ModelSetService modelSetService;

    @Transactional
    @Override
    public TrainingDto createTraining(TrainingDto trainingDto, long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new InvalidArgumentException("Workout doesn't exists"));
        Training newTraining = trainingRepository.save(new Training(workout));
        long id = newTraining.getId();
        trainingDto.getExercises().forEach(trainingExerciseDto ->
                modelSetService.createSets(trainingExerciseDto.getSets(), id,
                        trainingExerciseDto.getId())
        );
        logger.debug(String.format("Training with id %s correctly created", id));
        trainingDto.setId(id);
        trainingDto.setDate(newTraining.getDate());
        return trainingDto;
    }

    @Override
    public List<TrainingDto> getTrainingsByWorkoutId(long workoutId) {
        return trainingRepository.findAllByWorkoutId(workoutId).stream()
                .map(TrainingDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto getTrainingById(long workoutId, long trainingId) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new InvalidArgumentException(String.format("Training with id %s doesn't exist", trainingId)));
        if (training.getWorkout().getId() != workoutId) {
            throw new InvalidArgumentException("Training doesn't exist");
        }
        return new TrainingDto(training);
    }

    @Override
    public void deleteTraining(long id) {
        trainingRepository.deleteById(id);
        logger.debug(String.format("Training with id %s deleted correctly", id));
    }
}