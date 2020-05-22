package com.zti.workoutLogger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private LocalDate lastDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "workoutExercise",
            inverseJoinColumns = {@JoinColumn(name = "workoutId")},
            joinColumns = {@JoinColumn(name = "exerciseId")}
    )
    private Set<Workout> workouts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private Set<ModelSet> sets;

    public Exercise() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }

    public Set<ModelSet> getSets() {
        return sets;
    }

    public void setSets(Set<ModelSet> sets) {
        this.sets = sets;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }
}