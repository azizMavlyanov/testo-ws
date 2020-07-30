package com.testows.service.product;

import com.testows.dao.ProductRepository;
import com.testows.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity findOne(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Error("Product not found"));
    }
}
