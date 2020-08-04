package com.testows.dao;

import com.testows.entity.PurchaseEntity;
import com.testows.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseRepository extends PagingAndSortingRepository<PurchaseEntity, Long> {
    Page<PurchaseEntity> findByUser(UserEntity userEntity, Pageable pageable);
}
