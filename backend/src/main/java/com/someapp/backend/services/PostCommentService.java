package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.dto.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface PostCommentService {

    PostComment save(HttpServletRequest req, PostCommentSaveDTO postCommentSaveDTO);

    DeleteResponse delete(UUID postCommentId);
}
