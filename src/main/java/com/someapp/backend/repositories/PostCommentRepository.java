package com.someapp.backend.repositories;

import com.someapp.backend.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostCommentRepository
        extends JpaRepository<PostComment, UUID> {
}
