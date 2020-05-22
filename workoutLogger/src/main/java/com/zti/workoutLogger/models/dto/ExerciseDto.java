package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.utils.DateToStringConverter;

import java.time.LocalDate;

public class ExerciseDto {
    private long id;
    private String name;
    private String lastDate;

    public ExerciseDto() {
    }

    public ExerciseDto(String name) {
        this.name = name;
    }

    public ExerciseDto(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        LocalDate lastDate = exercise.getLastDate();
        if (lastDate != null) {
            this.lastDate = DateToStringConverter.convert(lastDate);
        }
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

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
