package com.testows.service.purchase;

import com.testows.entity.PurchaseEntity;
import com.testows.models.PurchaseRequestModel;
import com.testows.models.PurchaseResponseModel;

public interface PurchaseService {
    PurchaseEntity createOrder(Long userId, PurchaseRequestModel purchaseRequestModel) throws Exception;
}
