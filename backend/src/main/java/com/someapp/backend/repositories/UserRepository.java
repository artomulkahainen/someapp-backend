package com.someapp.backend.repositories;

import com.someapp.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    Optional<User> getByUUID(UUID uuid);
}
