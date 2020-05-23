package com.zti.workoutLogger.models.dto;

import com.zti.workoutLogger.utils.ChartInputsCreator;

import java.util.List;

public class WorkoutWithTrainingsDto {
    private WorkoutDto workout;
    private List<TrainingDto> trainings;
    private List<ChartInputDto> chartInputs;

    public WorkoutWithTrainingsDto(WorkoutDto workout, List<TrainingDto> trainings) {
        this.workout = workout;
        this.trainings = trainings;
        this.chartInputs = ChartInputsCreator.create(trainings);
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

    public List<ChartInputDto> getChartInputs() {
        return chartInputs;
    }

    public void setChartInputs(List<ChartInputDto> chartInputs) {
        this.chartInputs = chartInputs;
    }
}