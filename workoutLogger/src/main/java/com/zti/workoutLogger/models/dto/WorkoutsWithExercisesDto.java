package com.zti.workoutLogger.models.dto;

import java.util.List;

public class WorkoutsWithExercisesDto {
    private List<WorkoutDto> workouts;
    private List<ExerciseDto> exercises;

    public WorkoutsWithExercisesDto() {
    }

    public WorkoutsWithExercisesDto(List<WorkoutDto> workouts, List<ExerciseDto> exercises) {
        this.workouts = workouts;
        this.exercises = exercises;
    }

    public List<WorkoutDto> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<WorkoutDto> workouts) {
        this.workouts = workouts;
    }

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDto> exercises) {
        this.exercises = exercises;
    }
}