package com.zti.workoutLogger.services;

import com.zti.workoutLogger.models.dto.WorkoutDto;

import java.util.List;

public interface WorkoutService {

    List<WorkoutDto> getAllWorkoutsByUserId(long userId);

    WorkoutDto createWorkout(WorkoutDto workoutDto);
}