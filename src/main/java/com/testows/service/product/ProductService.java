package com.testows.service.product;

import com.testows.entity.ProductEntity;

public interface ProductService {
    ProductEntity findOne(Long productId);
}
