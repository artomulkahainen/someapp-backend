package com.someapp.backend.repositories;

import com.someapp.backend.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
}
