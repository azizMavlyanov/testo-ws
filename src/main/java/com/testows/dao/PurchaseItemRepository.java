package com.testows.dao;

import com.testows.entity.PurchaseEntity;
import com.testows.entity.PurchaseItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseItemRepository extends PagingAndSortingRepository<PurchaseItemEntity, Long> {
    Page<PurchaseItemEntity> findByPurchase(PurchaseEntity purchaseEntity, Pageable pageable);
}
