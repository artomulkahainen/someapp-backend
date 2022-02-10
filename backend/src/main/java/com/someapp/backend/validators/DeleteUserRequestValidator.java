package com.someapp.backend.validators;

import com.someapp.backend.dto.DeleteUserRequest;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class DeleteUserRequestValidator implements Validator {

    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public DeleteUserRequestValidator(JWTTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean supports(Class<?> clazz) {
        return DeleteUserRequest.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        DeleteUserRequest request = (DeleteUserRequest) target;

        if (!Objects.equals(jwtTokenUtil.getIdFromToken(req), request.getUuid())) {
            errors.reject("User can only delete their own user account");
        }
    }
}
