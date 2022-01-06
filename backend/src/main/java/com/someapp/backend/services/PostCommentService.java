package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.testUtility.requests.UUIDRequest;
import com.someapp.backend.testUtility.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;

public interface PostCommentService {

    PostComment save(HttpServletRequest req, PostCommentSaveDTO postCommentSaveDTO);

    DeleteResponse delete(HttpServletRequest req, UUIDRequest postCommentId);
}
