package com.zti.workoutLogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "workoutId", nullable = false)
    private Workout workout;

    public Training() {
    }

    public Training(Workout workout) {
        this.workout = workout;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}