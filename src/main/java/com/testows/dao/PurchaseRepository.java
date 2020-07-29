package com.testows.dao;

import com.testows.entity.PurchaseEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseRepository extends PagingAndSortingRepository<PurchaseEntity, Long> {
}
