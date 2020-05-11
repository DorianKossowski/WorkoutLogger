package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByWorkoutId(long workoutId);
}