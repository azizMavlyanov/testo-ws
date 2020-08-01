package com.testows.controllers;


import com.testows.entity.UserEntity;
import com.testows.models.*;
import com.testows.service.purchase.PurchaseService;
import com.testows.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getUsers(
            @RequestParam(value = "page", defaultValue = "1")
            @Min(value = 1, message = "page must be greater than 0")
                    int page,
            @Min(value = 1, message = "limit must be greater than 0")
            @RequestParam(value = "size", defaultValue = "10")
                    int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(page, size));
    }

    @GetMapping(
            value = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getUser(
            @Min(value = 1, message = "user ID must be greater than 0")
            @PathVariable(value = "userId") Long userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(userService.findOne(userId), UserResponseModel.class));
    };

    @PatchMapping(
            value = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> updateUser(
            @Min(value = 1, message = "user ID must be greater than 0")
            @PathVariable(value = "userId") Long userId,
            @Valid @RequestBody UserUpdateModel userUpdateModel
            )
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper
                        .map(userService.update(userId, userUpdateModel), UserResponseModel.class));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(
            @Min(value = 1, message = "user ID must be greater than 0")
            @PathVariable(value = "userId") Long userId
    ) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(
            value = "/{userId}/purchases",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createOrder(
            @Min(value = 1, message = "user ID must be greater than 0")
            @PathVariable(value = "userId") Long userId,
            @Valid @RequestBody PurchaseRequestModel purchaseRequestModel) {

        PurchaseResponseModel purchaseResponseModel = modelMapper.map(purchaseService.createOrder(userId, purchaseRequestModel),
                PurchaseResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseResponseModel);
    }
}
