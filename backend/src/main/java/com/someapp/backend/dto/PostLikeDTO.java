package com.someapp.backend.dto;

import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.sql.Timestamp;
import java.util.UUID;

public class PostLikeDTO extends BaseDTO {

    private UUID postId;

    public PostLikeDTO(UUID uuid, Timestamp createdDate, UUID postId) {
        super(uuid, createdDate);
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
