package com.someapp.backend.interfaces.repositories;

import com.someapp.backend.entities.User;
import com.someapp.backend.utils.customExceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
