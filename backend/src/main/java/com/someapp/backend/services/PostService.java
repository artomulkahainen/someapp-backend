package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.testUtility.requests.DeletePostRequest;
import com.someapp.backend.testUtility.requests.SendPostRequest;
import com.someapp.backend.testUtility.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface PostService {

    Post save(HttpServletRequest req, SendPostRequest sendPostRequest);

    DeleteResponse delete(HttpServletRequest req, DeletePostRequest deletePostRequest);

    List<Post> findPostsByRelationships(HttpServletRequest req);
}
