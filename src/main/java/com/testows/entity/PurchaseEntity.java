package com.testows.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.testows.models.OrderStatus;
import com.testows.models.PurchaseItem;
import com.testows.validators.ValueOfEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "purchase")
@DynamicUpdate
public class PurchaseEntity implements Serializable {
    private static final long serialVersionUID = 3943285337859572420L;

    public PurchaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseItemEntity> purchaseItemEntities = new ArrayList<>();

    @ValueOfEnum(enumClass = OrderStatus.class)
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String address;

    @Transient
    private float totalPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
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

    public void addPurchaseItem(PurchaseItemEntity purchaseItemEntity) {
        purchaseItemEntities.add(purchaseItemEntity);
        purchaseItemEntity.setPurchase(this);
    }

    public float getTotalPrice() {
        for (PurchaseItemEntity purchaseItemEntity : this.purchaseItemEntities) {
            totalPrice += ((float) purchaseItemEntity.getQuantity() * purchaseItemEntity.getProduct().getPrice());
        }

        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
