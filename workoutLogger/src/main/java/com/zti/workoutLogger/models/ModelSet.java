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
    private float weight;

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getVolume() {
        return this.reps * this.weight;
    }

    public String print() {
        String weightStr;
        if (this.weight == (long) this.weight) {
            weightStr = String.format("%d", (long) this.weight);
        } else {
            weightStr = String.format("%s", this.weight);
        }
        return this.reps + " X " + weightStr + "kg";
    }

    @PreRemove
    private void dismissSetFromTrainingAndExercise() {
        training.getSets().remove(this);
        exercise.getSets().remove(this);
        training = null;
        exercise = null;
    }
}