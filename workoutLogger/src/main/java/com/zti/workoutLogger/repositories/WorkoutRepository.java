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

    @Query("select case when count(w)> 0 then true else false end from Workout w " +
            "join w.exercises excs " +
            "where excs.user.id = ?2 and w.name = ?1")
    boolean existsByNameAndUserId(String name, long userId);
}