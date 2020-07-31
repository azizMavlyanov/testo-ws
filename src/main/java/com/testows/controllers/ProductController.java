package com.testows.controllers;

import com.testows.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping(value = "/{productId}/images")
    public ResponseEntity<?> uploadImage(@PathVariable(value = "productId") Long productId,
                                      @RequestParam("file") MultipartFile file) {

        try {
            productService.uploadImage(productId, file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/{productId}/images/{imageName}")
    public ResponseEntity<?> getImages(@PathVariable(value = "productId") Long productId,
                                       @PathVariable(value = "imageName") String imageName
                                       ) throws Exception {
        Resource file = productService.loadImage(productId, imageName);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
                "image/jpg").body(file);

    }
}
