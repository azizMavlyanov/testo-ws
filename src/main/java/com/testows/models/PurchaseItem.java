package com.testows.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.testows.entity.ProductEntity;

public class PurchaseItem {
    @JsonIgnoreProperties(value = {"category", "purchaseItemEntities", "createdAt", "updatedAt"})
    private ProductEntity product;
    private Long quantity;

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
