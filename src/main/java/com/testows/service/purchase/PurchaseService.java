package com.testows.service.purchase;

import com.testows.entity.PurchaseEntity;
import com.testows.models.*;

public interface PurchaseService {
    PurchaseEntity createOrder(Long userId, PurchaseRequestModel purchaseRequestModel) throws Exception;
    PageableAndSortableData<PurchaseResponseModel> getOrders(Long userId, int page, int size) throws Exception;
    PurchaseEntity findOne(Long userId, Long purchaseId) throws Exception;
    PurchaseEntity update(Long userId, Long purchaseId, PurchaseUpdateModel purchaseUpdateModel) throws Exception;
    PageableAndSortableData<PurchaseItemResponse> getPurchaseItems(Long userId,
                                                                   Long purchaseId,
                                                                   int page, int size) throws Exception;
}
