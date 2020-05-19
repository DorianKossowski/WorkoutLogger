package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.ModelSet;

public class ModelSetDto {
    private long id;
    private int reps;
    private float weight;

    public ModelSetDto() {
    }

    public ModelSetDto(ModelSet modelSet) {
        this.id = modelSet.getId();
        this.reps = modelSet.getReps();
        this.weight = modelSet.getWeight();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}