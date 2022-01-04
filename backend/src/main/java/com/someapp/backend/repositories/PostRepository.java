package com.someapp.backend.repositories;

import com.someapp.backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> getByUUID(UUID uuid);
}
