package com.someapp.backend.util.keys;

import java.io.Serializable;
import java.util.UUID;

public class PostLikeKey implements Serializable {
    private UUID userId;
    private UUID postId;
}