package com.testows.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.testows.entity.ProductEntity;
import com.testows.entity.PurchaseEntity;

public class PurchaseItemResponse {
    private Long purchaseItemId;
    @JsonIgnore
    private PurchaseEntity purchase;
    @JsonIgnoreProperties({"category", "purchaseItemEntities", "hibernateLazyInitializer"})
    private ProductEntity product;
    private Long quantity;

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
