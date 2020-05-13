package com.zti.workoutLogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "workoutId", nullable = false)
    private Workout workout;

    @JsonIgnore
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private Set<ModelSet> sets;

    public Training() {
    }

    public Training(Workout workout) {
        this.workout = workout;
        this.date = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Set<ModelSet> getSets() {
        return sets;
    }

    public void setSets(Set<ModelSet> sets) {
        this.sets = sets;
    }
}