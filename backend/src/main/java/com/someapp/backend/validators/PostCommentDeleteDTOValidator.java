package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentDeleteDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class PostCommentDeleteDTOValidator implements Validator {

    private final PostCommentRepository postCommentRepository;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public PostCommentDeleteDTOValidator(PostCommentRepository postCommentRepository, JWTTokenUtil jwtTokenUtil) {
        this.postCommentRepository = postCommentRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PostCommentDeleteDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        final PostCommentDeleteDTO postCommentDeleteDTO = (PostCommentDeleteDTO) target;

        Optional<PostComment> postComment = postCommentRepository.findById(postCommentDeleteDTO.getUuid());

        if (postComment.isPresent() && postComment.get().getUserId() != actionUserId) {
            errors.reject("User can delete only their own post comments");
        }
    }
}
