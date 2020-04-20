package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.Workout;

import java.util.Set;
import java.util.stream.Collectors;

public class WorkoutDto {
    private long id;
    private String name;
    private Set<Long> exercisesId;
    private Set<Exercise> exercises;

    public WorkoutDto() {
    }

    public WorkoutDto(String name, Set<Long> exercisesId) {
        this.name = name;
        this.exercisesId = exercisesId;
    }

    public WorkoutDto(Workout workout) {
        this.id = workout.getId();
        this.name = workout.getName();
        this.exercises = workout.getExercises();
        this.exercisesId = workout.getExercises().stream().map(Exercise::getId).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getExercisesId() {
        return exercisesId;
    }

    public void setExercisesId(Set<Long> exercisesId) {
        this.exercisesId = exercisesId;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}