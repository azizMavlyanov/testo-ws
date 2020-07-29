package com.testows.dao;

import com.testows.entity.PurchaseItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseItemRepository extends PagingAndSortingRepository<PurchaseItemEntity, Long> {
}
