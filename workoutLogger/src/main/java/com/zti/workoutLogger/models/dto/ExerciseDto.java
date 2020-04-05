package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Exercise;

public class ExerciseDto {
    private long id;
    private String name;

    public ExerciseDto(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
    }

    public long  getId() {
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
}
