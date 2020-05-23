package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.utils.ChartInputsCreator;

import java.util.List;
import java.util.Set;

public class ExerciseWithResultsDto {
    private ExerciseDto exercise;
    private Set<ResultDto> results;
    private List<ChartInputDto> chartInputs;

    public ExerciseWithResultsDto() {
    }

    public ExerciseWithResultsDto(ExerciseDto exercise, Set<ResultDto> results) {
        this.exercise = exercise;
        this.results = results;
        this.chartInputs = ChartInputsCreator.create(results);
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

    public List<ChartInputDto> getChartInputs() {
        return chartInputs;
    }

    public void setChartInputs(List<ChartInputDto> chartInputs) {
        this.chartInputs = chartInputs;
    }
}
