package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.TrainingExerciseDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.repositories.TrainingRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.ModelSetService;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.utils.DateToStringConverter;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ModelSetService modelSetService;

    @Transactional
    @Override
    public TrainingDto createTraining(TrainingDto trainingDto, long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new InvalidArgumentException("Workout doesn't exists"));
        Training newTraining = trainingRepository.save(new Training(workout));
        workout.setLastDate(LocalDate.now());
        workoutRepository.save(workout);
        long id = newTraining.getId();
        List<TrainingExerciseDto> exercises = trainingDto.getExercises();
        exercises.forEach(trainingExerciseDto ->
                modelSetService.createSets(trainingExerciseDto.getSets(), id,
                        trainingExerciseDto.getId())
        );
        Set<Long> exercisesId = exercises.stream()
                .map(TrainingExerciseDto::getId)
                .collect(Collectors.toSet());
        exerciseRepository.updateAllLastDateByIds(exercisesId, LocalDate.now());
        logger.debug(String.format("Training with id %s correctly created", id));
        trainingDto.setId(id);
        trainingDto.setDate(DateToStringConverter.convert(newTraining.getDate()));
        return trainingDto;
    }

    @Override
    public List<TrainingDto> getTrainingsByWorkoutId(long workoutId) {
        return trainingRepository.findAllByWorkoutIdOrderByDateDesc(workoutId).stream()
                .map(TrainingDto::new)
                .sorted(Comparator.comparing(TrainingDto::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto getTrainingById(long workoutId, long trainingId) {
        Training training = getTrainingWithValidation(workoutId, trainingId);
        return new TrainingDto(training);
    }

    @Override
    public void deleteTraining(long id) {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new InvalidArgumentException(String.format("Training with id %s doesn't exist", id)));

        modelSetService.deleteSets(training.getSets());
        trainingRepository.deleteById(id);
        logger.debug(String.format("Training with id %s deleted correctly", id));
    }

    @Transactional
    @Override
    public TrainingDto editTraining(TrainingDto trainingDto, long workoutId, long trainingId) {
        getTrainingWithValidation(workoutId, trainingId);
        trainingDto.getExercises().forEach(trainingExerciseDto ->
                modelSetService.editSets(trainingExerciseDto.getSets(), trainingId,
                        trainingExerciseDto.getId())
        );
        return trainingDto;
    }

    private Training getTrainingWithValidation(long workoutId, long trainingId) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new InvalidArgumentException(String.format("Training with id %s doesn't exist", trainingId)));
        if (training.getWorkout().getId() != workoutId) {
            throw new InvalidArgumentException("Training doesn't exist");
        }
        return training;
    }
}