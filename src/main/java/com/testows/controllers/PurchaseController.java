package com.testows.controllers;

import com.testows.models.PurchaseRequestModel;
import com.testows.models.PurchaseResponseModel;
import com.testows.models.PurchaseUpdateModel;
import com.testows.service.purchase.PurchaseService;
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
@RequestMapping("/api/v1/users/{userId}/purchases")
@Validated
public class PurchaseController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PurchaseService purchaseService;

//    @PostMapping(
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
//    )
//    public ResponseEntity<?> createPurchase(
//            @Min(value = 1, message = "user ID must be greater than 0")
//            @PathVariable(value = "userId") Long userId,
//            @Valid @RequestBody PurchaseRequestModel purchaseRequestModel) throws Exception {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(modelMapper.map(purchaseService.createOrder(userId, purchaseRequestModel),
//                        PurchaseResponseModel.class));
//    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createPurchase(
            @Min(value = 1, message = "user ID must be greater than 0")
            @PathVariable(value = "userId") Long userId,
            @Valid @RequestBody PurchaseRequestModel purchaseRequestModel) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(purchaseService.createOrder(userId, purchaseRequestModel),
                        PurchaseResponseModel.class));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPurchases(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "page", defaultValue = "1")
            @Min(value = 1, message = "page must be greater than 0")
                    int page,
            @Min(value = 1, message = "limit must be greater than 0")
            @RequestParam(value = "size", defaultValue = "10")
                    int size
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.getOrders(userId, page, size));
    }

    @GetMapping(
            value = "/{purchaseId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getPurchase(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "purchaseId") Long purchaseId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(purchaseService.findOne(userId, purchaseId), PurchaseResponseModel.class));
    }

    @PatchMapping(
            value = "/{purchaseId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> updatePurchase(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "purchaseId") Long purchaseId,
            @Valid @RequestBody PurchaseUpdateModel purchaseUpdateModel
    ) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modelMapper
                        .map(purchaseService
                                .update(userId, purchaseId, purchaseUpdateModel), PurchaseResponseModel.class));
    }

    @GetMapping(
            value = "/{purchaseId}/purchaseItems",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getPurchaseItems(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "userId") Long purchaseId,
            @RequestParam(value = "page", defaultValue = "1")
            @Min(value = 1, message = "page must be greater than 0")
                    int page,
            @Min(value = 1, message = "limit must be greater than 0")
            @RequestParam(value = "size", defaultValue = "10")
                    int size
    ) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(purchaseService.getPurchaseItems(userId, purchaseId, page, size));
    }

}
