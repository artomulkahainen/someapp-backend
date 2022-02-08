package com.someapp.backend.validators;

import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.services.PostService;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
public class DeletePostRequestValidator implements Validator {

    private final PostService postService;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest req;

    public DeletePostRequestValidator(PostService postService, JWTTokenUtil jwtTokenUtil) {
        this.postService = postService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeletePostRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeletePostRequest request = (DeletePostRequest) target;
        Optional<Post> postToDelete = postService.findPostById(request.getUuid());
        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        if (postToDelete.isPresent() && !postToDelete.get().getUserId().equals(actionUserId)) {
            errors.reject("Posts can be deleted only by owners.");
        }
    }


}
