package com.zti.workoutLogger.services.impl;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.dto.ExerciseDto;
import com.zti.workoutLogger.repositories.ExerciseRepository;
import com.zti.workoutLogger.services.ExerciseService;
import com.zti.workoutLogger.utils.auth.AuthenticatedUserGetter;
import com.zti.workoutLogger.utils.exceptions.AlreadyExistsException;
import com.zti.workoutLogger.utils.exceptions.InvalidArgumentExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    @Autowired
    private AuthenticatedUserGetter userGetter;
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseDto> getAllExercisesByUserId(long userId) {
        return exerciseRepository.findAllByUserId(userId).stream()
                .map(ExerciseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDto createExercise(ExerciseDto newExerciseDto) {
        if (newExerciseDto.getName().isEmpty()) {
            throw new InvalidArgumentExceptions("Name cannot be empty");
        }
        if (exerciseRepository.existsByName(newExerciseDto.getName())) {
            throw new AlreadyExistsException(newExerciseDto.getName());
        }
        Exercise savedExercise = exerciseRepository.save(getNewExercise(newExerciseDto));
        logger.debug(newExerciseDto.getName() + " created successfully");
        return new ExerciseDto(savedExercise);
    }

    private Exercise getNewExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setUser(userGetter.get());
        return exercise;
    }
}