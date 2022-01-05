package com.someapp.backend.interfaces.repositories;

import com.someapp.backend.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
    Optional<PostLike> findByUserUUIDAndPostUUID(UUID userUUID, UUID postUUID);
}
