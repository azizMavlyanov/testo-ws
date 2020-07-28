package com.testows.service.category;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.PageableAndSortableData;
import com.testows.models.ProductResponseModel;

import java.util.List;

public interface CategoryService {
    CategoryEntity create(CategoryEntity categoryEntity);
    PageableAndSortableData<CategoryEntity> findAll(int page, int size);
    CategoryEntity findOne(Long categoryId);
    CategoryEntity update(Long categoryId, CategoryEntity categoryEntity);
    void delete(Long categoryId);
    ProductEntity addProduct(Long categoryId, ProductEntity productEntity);
    PageableAndSortableData<ProductResponseModel> getProducts(Long categoryId, int page, int size);
}
