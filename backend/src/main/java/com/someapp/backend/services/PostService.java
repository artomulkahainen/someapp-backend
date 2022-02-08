package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface PostService {

    Post save(HttpServletRequest req, SendPostRequest sendPostRequest);

    DeleteResponse delete(DeletePostRequest deletePostRequest);

    List<Post> findPostsByRelationships(HttpServletRequest req);

    Optional<Post> findPostById(UUID uuid);
}
