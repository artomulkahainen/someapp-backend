package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.utils.requests.UUIDRequest;
import com.someapp.backend.utils.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface PostCommentService {

    PostComment save(HttpServletRequest req, PostCommentSaveDTO postCommentSaveDTO);

    DeleteResponse delete(HttpServletRequest req, UUID postCommentId);
}
