package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.RelationshipDTO;
import com.someapp.backend.testUtility.requests.ModifyRelationshipRequest;
import com.someapp.backend.testUtility.requests.NewRelationshipRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface RelationshipApi {

    @GetMapping("/getUserRelationshipsByUsingGET")
    List<RelationshipDTO> getUserRelationships(@NotNull HttpServletRequest req);

    @PostMapping("/saveNewRelationshipByUsingPOST")
    RelationshipDTO saveNewRelationship(@NotNull HttpServletRequest req,
                                        @Valid @RequestBody NewRelationshipRequest relationshipRequest,
                                        BindingResult bindingResult) throws BindException;

    @PutMapping("/updateRelationshipByUsingPUT")
    RelationshipDTO updateRelationship(@NotNull HttpServletRequest req,
                                       @Valid @RequestBody ModifyRelationshipRequest modifyRelationshipRequest,
                                       BindingResult bindingResult) throws BindException;
}
