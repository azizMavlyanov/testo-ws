package com.testows.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.testows.entity.PurchaseItemEntity;
import com.testows.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class PurchaseResponseModel {
    private Long purchaseId;
    @JsonIgnoreProperties(value = {"purchases", "userId"})
    private UserEntity user;
    private List<PurchaseItem> purchaseItemEntities;
    private String status;
    private String address;
    private float totalPrice;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonProperty("purchaseItems")
    public List<PurchaseItem> getPurchaseItemEntities() {
        return purchaseItemEntities;
    }

    public void setPurchaseItemEntities(List<PurchaseItemEntity> purchaseItemEntities) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();

        for (PurchaseItemEntity purchaseItemEntity : purchaseItemEntities) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setProduct(purchaseItemEntity.getProduct());
            purchaseItem.setQuantity(purchaseItemEntity.getQuantity());
            purchaseItems.add(purchaseItem);
        }

        this.purchaseItemEntities = purchaseItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotalPrice() {
        float result = 0;

        for (PurchaseItem purchaseItem : this.purchaseItemEntities) {
            result += ((float) purchaseItem.getQuantity() * purchaseItem.getProduct().getPrice());
        }
        return result;
    }

    public void setTotalPrice(float totalPrice) {

        this.totalPrice = totalPrice;
    }
}
