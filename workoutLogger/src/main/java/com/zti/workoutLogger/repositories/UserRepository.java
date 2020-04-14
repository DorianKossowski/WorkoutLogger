package com.zti.workoutLogger.repositories;

import com.zti.workoutLogger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMail(String mail);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}