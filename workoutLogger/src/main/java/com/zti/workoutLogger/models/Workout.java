package com.zti.workoutLogger.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workoutExercise",
            joinColumns = {@JoinColumn(name = "workoutId")},
            inverseJoinColumns = {@JoinColumn(name = "exerciseId")}
    )
    private Set<Exercise> exercises = new HashSet<>();

    public Workout() {
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

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}