package com.testows.controllers;


import com.testows.entity.UserEntity;
import com.testows.models.PurchaseRequestModel;
import com.testows.models.PurchaseResponseModel;
import com.testows.models.UserRequestModel;
import com.testows.models.UserResponseModel;
import com.testows.service.purchase.PurchaseService;
import com.testows.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestModel userRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(userService.create(modelMapper.map(userRequestModel, UserEntity.class)),
                        UserResponseModel.class));
    }

    @PostMapping(
            value = "/{userId}/purchases",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createOrder(
            @PathVariable(value = "userId") Long userId,
            @Valid @RequestBody PurchaseRequestModel purchaseRequestModel) {

        PurchaseResponseModel purchaseResponseModel = modelMapper.map(purchaseService.createOrder(userId, purchaseRequestModel),
                PurchaseResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseResponseModel);
    }
}
