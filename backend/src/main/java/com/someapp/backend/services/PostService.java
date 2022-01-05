package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.util.requests.DeletePostRequest;
import com.someapp.backend.util.requests.SendPostRequest;
import com.someapp.backend.util.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface PostService {

    Post save(HttpServletRequest req, SendPostRequest sendPostRequest);

    DeleteResponse delete(HttpServletRequest req, DeletePostRequest deletePostRequest);

    List<Post> findPostsByRelationships(HttpServletRequest req);
}
