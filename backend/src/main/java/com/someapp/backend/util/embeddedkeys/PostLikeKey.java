package com.someapp.backend.util.embeddedkeys;

import java.io.Serializable;
import java.util.UUID;

public class PostLikeKey implements Serializable {
    private UUID userId;
    private UUID postId;
}