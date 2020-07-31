package com.testows.service.product;

import com.testows.entity.ProductEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductEntity findOne(Long productId);
    void uploadImage(Long productId, MultipartFile file) throws Exception;
    Resource loadImage(Long productId, String imageName) throws Exception;
}
