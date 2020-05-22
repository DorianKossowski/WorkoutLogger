package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndUserId(String name, long userId);

    List<Exercise> findAllByUserId(long userId);

    Set<Exercise> findByIdIn(Set<Long> idList);

    @Modifying
    @Query("update Exercise e set e.lastDate=:date where e.id in :ids")
    void updateAllLastDateByIds(Set<Long> ids, LocalDate date);
}
