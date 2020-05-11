package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingDto {
    private long id;
    private LocalDate date;
    private List<TrainingExerciseDto> exercises;

    public TrainingDto() {
    }

    public TrainingDto(Training training) {
        this.id = training.getId();
        this.date = training.getDate();
        this.exercises = training.getWorkout().getExercises().stream()
                .map(TrainingExerciseDto::new)
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TrainingExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<TrainingExerciseDto> exercises) {
        this.exercises = exercises;
    }
}