package com.zti.workoutLogger.models.dto;

import java.util.Set;

public class ExerciseWithResultsDto {
    private ExerciseDto exercise;
    Set<ResultDto> results;

    public ExerciseWithResultsDto() {
    }

    public ExerciseWithResultsDto(ExerciseDto exercise, Set<ResultDto> results) {
        this.exercise = exercise;
        this.results = results;
    }

    public ExerciseDto getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDto exercise) {
        this.exercise = exercise;
    }

    public Set<ResultDto> getResults() {
        return results;
    }

    public void setResults(Set<ResultDto> results) {
        this.results = results;
    }
}
