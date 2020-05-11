package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}