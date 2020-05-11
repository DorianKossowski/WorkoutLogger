package com.zti.workoutLogger.models.dto;

import java.util.List;

public class TrainingDto {
    private long trainingId;
    private List<TrainingExerciseDto> exercises;

    public TrainingDto() {
    }

    public TrainingDto(long trainingId, List<TrainingExerciseDto> exercises) {
        this.trainingId = trainingId;
        this.exercises = exercises;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public List<TrainingExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<TrainingExerciseDto> exercises) {
        this.exercises = exercises;
    }
}