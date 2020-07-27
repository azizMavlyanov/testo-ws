package com.testows.service.category;

import com.testows.entity.CategoryEntity;
import com.testows.models.PageableAndSortableData;

public interface CategoryService {
    CategoryEntity create(CategoryEntity categoryEntity);
    PageableAndSortableData<CategoryEntity> findAll(int page, int size);
    CategoryEntity findOne(Long categoryId);
}
