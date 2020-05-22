package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.TrainingDto;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.models.dto.WorkoutWithTrainingsDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.TrainingService;
import com.zti.workoutLogger.services.WorkoutService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.ForbiddenException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// TODO refactor -> common stuff from Workout and Exercise
@Service
public class WorkoutServiceImpl implements WorkoutService {
    private static final Logger logger = LoggerFactory.getLogger(WorkoutServiceImpl.class);

    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TrainingService trainingService;

    @Override
    public List<WorkoutDto> getAllWorkoutsByUserId(long userId) {
        return workoutRepository.findAllByUserId(userId).stream()
                .map(WorkoutDto::new)
                .sorted(Comparator.comparing(WorkoutDto::getLastDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        String name = workoutDto.getName();
        validateWorkoutName(name);
        if (workoutDto.getExercisesId().isEmpty()) {
            throw new InvalidArgumentException("Select at least one exercise");
        }
        Workout savedWorkout = workoutRepository.save(getNewWorkout(workoutDto));
        logger.debug(name + " created successfully");
        return new WorkoutDto(savedWorkout);
    }

    private void validateWorkoutName(String name) {
        if (name.isEmpty()) {
            throw new InvalidArgumentException("Name cannot be empty");
        }
        if (workoutRepository.existsByNameAndUserId(name, userGetter.get().getId())) {
            throw new AlreadyExistsException(name);
        }
    }

    private Workout getNewWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setExercises(exerciseRepository.findByIdIn(workoutDto.getExercisesId()));
        return workout;
    }

    @Override
    public WorkoutWithTrainingsDto getWorkoutById(long id) {
        WorkoutDto workoutDto = new WorkoutDto(getWorkoutWithValidation(id));
        List<TrainingDto> trainingsByWorkoutId = trainingService.getTrainingsByWorkoutId(id);
        return new WorkoutWithTrainingsDto(workoutDto, trainingsByWorkoutId);
    }

    private Workout getWorkoutWithValidation(long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new InvalidArgumentException("Workout doesn't exist"));
        long workoutUserId = workout.getExercises().iterator().next().getUser().getId();
        if (workoutUserId != userGetter.get().getId()) {
            throw new ForbiddenException();
        }
        return workout;
    }

    @Override
    public WorkoutDto editWorkout(WorkoutDto workoutDto, long id) {
        Workout workout = getWorkoutWithValidation(id);
        String newName = workoutDto.getName();
        if (newName.equals(workout.getName())) {
            return new WorkoutDto(workout);
        }
        validateWorkoutName(newName);
        workout.setName(newName);
        Workout updatedWorkout = workoutRepository.save(workout);
        return new WorkoutDto(updatedWorkout);
    }

    @Override
    @Transactional
    public void deleteWorkout(long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new InvalidArgumentException("Workout doesn't exist"));

        for (Iterator<Training> it = workout.getTrainings().iterator(); it.hasNext(); ) {
            Training training = it.next();
            it.remove();
            trainingService.deleteTraining(training.getId());
        }

        workoutRepository.deleteById(id);
        logger.debug(String.format("Workout with id %s deleted correctly", id));
    }
}