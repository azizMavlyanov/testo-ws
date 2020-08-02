package com.testows.dao;

import com.testows.entity.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}
