package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.ExerciseDto;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAllExercisesByUserId(long userId);

    ExerciseDto createExercise(ExerciseDto newExerciseDto);

    ExerciseDto getExerciseById(long id);

    ExerciseDto editExercise(ExerciseDto exerciseDto, long id);

    void deleteExercise(long id);
}