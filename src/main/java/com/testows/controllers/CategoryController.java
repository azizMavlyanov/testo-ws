package com.testows.controllers;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.*;
import com.testows.service.category.CategoryService;
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
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestModel categoryRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(categoryService
                                .create(modelMapper.map(categoryRequestModel, CategoryEntity.class)),
                        CategoryResponseModel.class));
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getCategories(
            @RequestParam(value = "page", defaultValue = "1")
            @Min(value = 1, message = "page must be greater than 0")
                    int page,
            @Min(value = 1, message = "limit must be greater than 0")
            @RequestParam(value = "size", defaultValue = "10")
                    int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(page, size));
    }

    @GetMapping(
            value = "/{categoryId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getCategory(@PathVariable(value = "categoryId")
                                         @Min(value = 1, message = "categoryId must be greater than 0")
                                                 Long categoryId) {

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper
                .map(categoryService.findOne(categoryId), CategoryResponseModel.class));
    }
    @PatchMapping(
            value = "/{categoryId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> updateCategory(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId,
            @Valid @RequestBody CategoryUpdateModel categoryUpdateModel
            )
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.update(categoryId, modelMapper.map(categoryUpdateModel, CategoryEntity.class)));
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId)
    {
        categoryService.delete(categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(
            value = "/{categoryId}/products",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> addProduct(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "categoryId must be greater than 0")
                    Long categoryId,
            @Valid @RequestBody ProductRequestModel productRequestModel
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        modelMapper.map(categoryService
                                .addProduct(categoryId, modelMapper.map(productRequestModel, ProductEntity.class)),
                                ProductResponseModel.class));

    }

    @GetMapping(
            value = "/{categoryId}/products",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> getProducts(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "categoryId must be greater than 0")
                    Long categoryId,
            @RequestParam(value = "page", defaultValue = "1")
            @Min(value = 1, message = "page must be greater than 0")
                    int page,
            @Min(value = 1, message = "limit must be greater than 0")
            @RequestParam(value = "size", defaultValue = "10")
                    int size
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getProducts(categoryId, page, size));
    }

    @PatchMapping(value = "/{categoryId}/products/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId,
            @PathVariable(value = "productId")
            @Min(value = 1, message = "Product ID must be greater than 0")
                                     Long productId,
            @Valid @RequestBody ProductUpdateModel productUpdateModel) {


        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(categoryService
                        .updateProduct(categoryId, productId, modelMapper.map(productUpdateModel, ProductEntity.class)),
                        ProductResponseModel.class));
    }
}
