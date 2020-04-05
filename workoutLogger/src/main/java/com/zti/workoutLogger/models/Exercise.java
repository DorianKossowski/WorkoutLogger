package com.zti.workoutLogger.models;

import javax.persistence.*;

@Entity
public class Exercise {
    @Id
    private long id;

    @Column
    private String name;

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
}