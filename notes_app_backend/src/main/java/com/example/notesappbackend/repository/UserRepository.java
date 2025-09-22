package com.example.notesappbackend.repository;

import com.example.notesappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // PUBLIC_INTERFACE
    Optional<User> findByUsername(String username);

    // PUBLIC_INTERFACE
    boolean existsByUsername(String username);
}
