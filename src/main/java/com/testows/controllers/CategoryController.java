package com.testows.controllers;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.*;
import com.testows.service.category.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestModel categoryRequestModel) throws Exception {
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
            ) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(categoryService.update(categoryId,
                        modelMapper.map(categoryUpdateModel, CategoryEntity.class)), CategoryResponseModel.class));
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

    @PostMapping(value = "/{categoryId}/images")
    public ResponseEntity<?> uploadImage(@PathVariable(value = "categoryId") Long categoryId,
                                         @RequestParam("file") MultipartFile file) throws Exception {
        try {
            categoryService.uploadImage(categoryId, file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/{categoryId}/images/{imageName}")
    public ResponseEntity<?> getImages(@PathVariable(value = "categoryId") Long categoryId,
                                       @PathVariable(value = "imageName") String imageName
    ) throws Exception {
        Resource file = categoryService.loadImage(categoryId, imageName);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
                "image/jpg").body(file);

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
    ) throws Exception
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

    @GetMapping(value = "/{categoryId}/products/{productId}")
    public ResponseEntity<?> getProduct(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId,
            @PathVariable(value = "productId")
            @Min(value = 1, message = "Product ID must be greater than 0")
                    Long productId
    )
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(categoryService.getProduct(categoryId, productId), ProductResponseModel.class));
    }

    @PatchMapping(value = "/{categoryId}/products/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId,
            @PathVariable(value = "productId")
            @Min(value = 1, message = "Product ID must be greater than 0")
                                     Long productId,
            @Valid @RequestBody ProductUpdateModel productUpdateModel) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(categoryService
                        .updateProduct(categoryId, productId, modelMapper.map(productUpdateModel, ProductEntity.class)),
                        ProductResponseModel.class));
    }

    @DeleteMapping(value = "/{categoryId}/products/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable(value = "categoryId")
            @Min(value = 1, message = "Category ID must be greater than 0")
                    Long categoryId,
            @PathVariable(value = "productId")
            @Min(value = 1, message = "Product ID must be greater than 0")
                    Long productId
    ) throws Exception
    {
        categoryService.deleteProduct(categoryId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
