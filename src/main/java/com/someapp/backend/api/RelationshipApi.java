package com.someapp.backend.api;

import com.someapp.backend.dto.*;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface RelationshipApi {

    @PostMapping("/saveNewRelationshipByUsingPOST")
    RelationshipDTO save(@Valid @RequestBody SaveRelationshipDTO saveRelationshipDTO,
                         BindingResult bindingResult) throws BindException;

    @PostMapping("/declineRelationshipByUsingPOST")
    StatusResponse decline(@Valid @RequestBody DeclineRelationshipRequest declineRelationshipRequest,
                           BindingResult bindingResult) throws Exception;
}
