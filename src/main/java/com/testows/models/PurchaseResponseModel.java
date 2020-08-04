package com.testows.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.testows.entity.PurchaseItemEntity;
import com.testows.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseResponseModel {
    private Long purchaseId;
    @JsonIgnoreProperties(value = {"purchases", "userId"})
    private UserEntity user;
    @JsonIgnore
    private List<PurchaseItemEntity> purchaseItemEntities;
    private String status;
    private String address;
    private float totalPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="UTC")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="UTC")
    private LocalDateTime updatedAt;

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

    public List<PurchaseItemEntity> getPurchaseItemEntities() {
        return purchaseItemEntities;
    }

    public void setPurchaseItemEntities(List<PurchaseItemEntity> purchaseItemEntities) {
        this.purchaseItemEntities = purchaseItemEntities;
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
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //    private Long purchaseId;
//    @JsonIgnoreProperties(value = {"purchases", "userId"})
//    private UserEntity user;
//    private List<PurchaseItem> purchaseItemEntities;
//    private String status;
//    private String address;
//    private float totalPrice;
//
//    public Long getPurchaseId() {
//        return purchaseId;
//    }
//
//    public void setPurchaseId(Long purchaseId) {
//        this.purchaseId = purchaseId;
//    }
//
//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }
//
//    @JsonProperty("purchaseItems")
//    public List<PurchaseItem> getPurchaseItemEntities() {
//        return purchaseItemEntities;
//    }
//
//    public void setPurchaseItemEntities(List<PurchaseItemEntity> purchaseItemEntities) {
//        List<PurchaseItem> purchaseItems = new ArrayList<>();
//
//        for (PurchaseItemEntity purchaseItemEntity : purchaseItemEntities) {
//            PurchaseItem purchaseItem = new PurchaseItem();
//            purchaseItem.setProduct(purchaseItemEntity.getProduct());
//            purchaseItem.setQuantity(purchaseItemEntity.getQuantity());
//            purchaseItems.add(purchaseItem);
//        }
//
//        this.purchaseItemEntities = purchaseItems;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public float getTotalPrice() {
//        for (PurchaseItem purchaseItem : this.purchaseItemEntities) {
//            totalPrice += ((float) purchaseItem.getQuantity() * purchaseItem.getProduct().getPrice());
//        }
//        return totalPrice;
//    }
//
//    public void setTotalPrice(float totalPrice) {
//
//        this.totalPrice = totalPrice;
//    }
}
