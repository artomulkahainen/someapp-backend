package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.RelationshipDTO;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface RelationshipApi {

    @PostMapping("/saveNewRelationshipByUsingPOST")
    RelationshipDTO save(@Valid @RequestBody RelationshipDTO relationshipDTO,
                                        BindingResult bindingResult) throws BindException;

    /*@PostMapping("/declineRelationshipByUsingPOST")
    DeleteResponse decline()*/
}
