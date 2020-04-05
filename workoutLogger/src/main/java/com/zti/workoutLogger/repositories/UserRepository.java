package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.Exercise;
import com.zti.workoutLogger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
