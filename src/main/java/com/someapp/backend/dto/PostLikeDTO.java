package com.someapp.backend.dto;

import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.UUID;

public class PostLikeDTO extends BaseDTO {

    private UUID postId;

    public PostLikeDTO(UUID uuid, Date createdDate, UUID postId) {
        super(uuid, createdDate);
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
