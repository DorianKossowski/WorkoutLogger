package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.services.ExerciseService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private WorkoutService workoutService;

    @Override
    public List<ExerciseDto> getAllExercisesByUserId(long userId) {
        return exerciseRepository.findAllByUserId(userId).stream()
                .map(ExerciseDto::new)
                .sorted(Comparator.comparing(ExerciseDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDto createExercise(ExerciseDto newExerciseDto) {
        String exerciseName = newExerciseDto.getName();
        validateExerciseName(exerciseName);
        Exercise savedExercise = exerciseRepository.save(getNewExercise(newExerciseDto));
        logger.debug(exerciseName + " created successfully");
        return new ExerciseDto(savedExercise);
    }

    private void validateExerciseName(String exerciseName) {
        if (exerciseName.isEmpty()) {
            throw new InvalidArgumentException("Name cannot be empty");
        }
        if (exerciseRepository.existsByNameAndUserId(exerciseName, userGetter.get().getId())) {
            throw new AlreadyExistsException(exerciseName);
        }
    }

    private Exercise getNewExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setUser(userGetter.get());
        return exercise;
    }

    @Override
    public ExerciseDto getExerciseById(long id) {
        return new ExerciseDto(getExerciseWithValidation(id));
    }

    private Exercise getExerciseWithValidation(long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new InvalidArgumentException("Exercise doesn't exist"));
        if (exercise.getUser().getId() != userGetter.get().getId()) {
            throw new ForbiddenException();
        }
        return exercise;
    }

    @Override
    public ExerciseDto editExercise(ExerciseDto exerciseDto, long id) {
        Exercise exercise = getExerciseWithValidation(id);
        String newName = exerciseDto.getName();
        if (newName.equals(exercise.getName())) {
            return new ExerciseDto(exercise);
        }
        validateExerciseName(newName);
        exercise.setName(newName);
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return new ExerciseDto(updatedExercise);
    }

    @Transactional
    @Override
    public void deleteExercise(long id) {
        Exercise exerciseWithValidation = getExerciseWithValidation(id);
        exerciseWithValidation.getWorkouts().forEach(workout -> {
            if (workout.getExercises().size() == 1) {
                workoutService.deleteWorkout(workout.getId());
            }
        });
        exerciseRepository.deleteById(id);
        logger.debug(String.format("Exercise with id %s deleted correctly", id));
    }
}