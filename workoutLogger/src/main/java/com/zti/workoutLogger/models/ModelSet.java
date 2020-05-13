package com.zti.workoutLogger.models;

import com.zti.workoutLogger.models.dto.ModelSetDto;

import javax.persistence.*;

@Entity(name = "set")
public class ModelSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "trainingId")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "exerciseId")
    private Exercise exercise;

    @Column
    private int reps;
    @Column
    private int weight;

    public ModelSet() {
    }

    public ModelSet(Training training, Exercise exercise, ModelSetDto modelSetDto) {
        this.training = training;
        this.exercise = exercise;
        this.reps = modelSetDto.getReps();
        this.weight = modelSetDto.getWeight();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}