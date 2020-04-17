package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    boolean existsByName(String name);

    @Query("select distinct w from Workout w " +
            "join w.exercises excs " +
            "where excs.user.id = ?1")
    List<Workout> findAllByUserId(long userId);
}