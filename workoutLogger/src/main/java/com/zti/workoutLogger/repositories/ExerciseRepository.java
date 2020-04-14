package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    boolean existsByName(String name);

    List<Exercise> findAllByUserId(long userId);
}
