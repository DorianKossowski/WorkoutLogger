package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.Training;
import com.zti.workoutLogger.utils.DateToStringConverter;

import java.util.*;
import java.util.stream.Collectors;

public class TrainingDto {
    private long id;
    private String date;
    private List<TrainingExerciseDto> exercises;

    public TrainingDto() {
    }

    public TrainingDto(Training training) {
        this.id = training.getId();
        this.date = DateToStringConverter.convert(training.getDate());
        this.exercises = getExercises(training);
    }

    private List<TrainingExerciseDto> getExercises(Training training) {
        List<TrainingExerciseDto> exerciseDtos = new ArrayList<>();
        Set<Exercise> exercises = training.getWorkout().getExercises().stream()
                .sorted(Comparator.comparing(Exercise::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        exercises.forEach(exercise -> {
            List<ModelSetDto> modelSetDtos = training.getSets().stream()
                    .filter(modelSet -> modelSet.getExercise().equals(exercise))
                    .map(ModelSetDto::new)
                    .collect(Collectors.toList());
            exerciseDtos.add(new TrainingExerciseDto(exercise, modelSetDtos));
        });
        return exerciseDtos;
    }

    public TrainingDto(List<TrainingExerciseDto> trainingExercises) {
        this.exercises = trainingExercises;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TrainingExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<TrainingExerciseDto> exercises) {
        this.exercises = exercises;
    }
}