package com.testows.dao;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
    Page<ProductEntity> findByCategory(CategoryEntity categoryEntity, Pageable pageable);
}
