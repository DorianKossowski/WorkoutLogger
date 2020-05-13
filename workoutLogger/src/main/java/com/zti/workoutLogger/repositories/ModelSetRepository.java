package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.ModelSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelSetRepository extends JpaRepository<ModelSet, Long> {
}