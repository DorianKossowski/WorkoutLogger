package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Workout;
import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.repositories.WorkoutRepository;
import com.zti.workoutLogger.services.WorkoutService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.ForbiddenException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    @Override
    public List<WorkoutDto> getAllWorkoutsByUserId(long userId) {
        return workoutRepository.findAllByUserId(userId).stream()
                .map(WorkoutDto::new)
                .sorted(Comparator.comparing(WorkoutDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        if (workoutDto.getName().isEmpty()) {
            throw new InvalidArgumentException("Name cannot be empty");
        }
        if (workoutDto.getExercisesId().isEmpty()) {
            throw new InvalidArgumentException("Select at least one exercise");
        }
        if (workoutRepository.existsByName(workoutDto.getName())) {
            throw new AlreadyExistsException(workoutDto.getName());
        }
        Workout savedWorkout = workoutRepository.save(getNewWorkout(workoutDto));
        logger.debug(workoutDto.getName() + " created successfully");
        return new WorkoutDto(savedWorkout);
    }

    private Workout getNewWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setExercises(exerciseRepository.findByIdIn(workoutDto.getExercisesId()));
        return workout;
    }

    @Override
    public WorkoutDto getWorkoutById(long id) {
        return new WorkoutDto(getWorkoutWithValidation(id));
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
        if (newName.isEmpty()) {
            throw new InvalidArgumentException("Name cannot be empty");
        }
        if (newName.equals(workout.getName())) {
            return new WorkoutDto(workout);
        }
        workout.setName(newName);
        Workout updatedWorkout = workoutRepository.save(workout);
        return new WorkoutDto(updatedWorkout);
    }
}