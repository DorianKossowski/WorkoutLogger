package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Exercise;

import java.util.Collections;
import java.util.List;

public class TrainingExerciseDto {
    private long id;
    private String name;
    private List<ModelSetDto> sets;

    public TrainingExerciseDto() {
    }

    public TrainingExerciseDto(Exercise exercise, List<ModelSetDto> sets) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.sets = sets;
    }

    public TrainingExerciseDto(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.sets = Collections.emptyList();
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

    public List<ModelSetDto> getSets() {
        return sets;
    }

    public void setSets(List<ModelSetDto> sets) {
        this.sets = sets;
    }

    public float getVolume() {
        return sets.stream().map(ModelSetDto::getVolume).reduce(0f, Float::sum);
    }
}