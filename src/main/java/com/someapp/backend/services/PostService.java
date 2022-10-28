package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.dto.DeleteResponse;

import java.util.*;

public interface PostService {

    Post save(SendPostRequest sendPostRequest);

    DeleteResponse delete(DeletePostRequest deletePostRequest);

    List<Post> findPostsByRelationships();

    Optional<Post> findPostById(UUID uuid);
}
