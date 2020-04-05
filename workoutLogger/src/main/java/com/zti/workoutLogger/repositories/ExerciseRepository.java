package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
