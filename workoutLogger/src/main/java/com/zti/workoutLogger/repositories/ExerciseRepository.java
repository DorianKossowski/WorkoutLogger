package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndUserId(String name, long userId);

    List<Exercise> findAllByUserId(long userId);

    Set<Exercise> findByIdIn(Set<Long> idList);
}
