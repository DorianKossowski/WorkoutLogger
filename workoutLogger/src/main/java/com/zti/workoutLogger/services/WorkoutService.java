package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.WorkoutDto;
import com.zti.workoutLogger.models.dto.WorkoutWithTrainingsDto;

import java.util.List;

public interface WorkoutService {

    List<WorkoutDto> getAllWorkoutsByUserId(long userId);

    WorkoutDto createWorkout(WorkoutDto workoutDto);

    WorkoutWithTrainingsDto getWorkoutById(long id);

    WorkoutDto editWorkout(WorkoutDto workoutDto, long id);

    void deleteWorkout(long id);
}