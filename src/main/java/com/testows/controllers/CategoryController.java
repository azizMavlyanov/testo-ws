package com.testows.controllers;

import com.testows.entity.CategoryEntity;
import com.testows.models.CategoryRequestModel;
import com.testows.models.CategoryResponseModel;
import com.testows.models.CategoryUpdateModel;
import com.testows.models.PageableAndSortableData;
import com.testows.service.category.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/api/v1/categories")
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
}
