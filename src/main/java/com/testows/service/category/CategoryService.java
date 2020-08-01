package com.testows.service.category;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.CategoryResponseModel;
import com.testows.models.PageableAndSortableData;
import com.testows.models.ProductResponseModel;

import java.util.List;

public interface CategoryService {
    CategoryEntity create(CategoryEntity categoryEntity);
    PageableAndSortableData<CategoryResponseModel> findAll(int page, int size);
    CategoryEntity findOne(Long categoryId);
    CategoryEntity update(Long categoryId, CategoryEntity categoryEntity);
    void delete(Long categoryId);
    ProductEntity addProduct(Long categoryId, ProductEntity productEntity);
    PageableAndSortableData<ProductResponseModel> getProducts(Long categoryId, int page, int size);
    ProductEntity updateProduct(Long categoryId, Long productId, ProductEntity productEntity);
    ProductEntity getProduct(Long categoryId, Long productId);
    void deleteProduct(Long categoryId, Long productId);
}
