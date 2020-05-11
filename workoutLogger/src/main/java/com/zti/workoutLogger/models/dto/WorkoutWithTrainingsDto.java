package com.zti.workoutLogger.models.dto;

import java.util.List;

public class WorkoutWithTrainingsDto {
    private WorkoutDto workout;
    private List<TrainingDto> trainings;

    public WorkoutWithTrainingsDto(WorkoutDto workout, List<TrainingDto> trainings) {
        this.workout = workout;
        this.trainings = trainings;
    }

    public WorkoutDto getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutDto workout) {
        this.workout = workout;
    }

    public List<TrainingDto> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingDto> trainings) {
        this.trainings = trainings;
    }
}