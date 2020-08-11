package com.testows.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity(name = "purchase_item")
@DynamicUpdate

public class PurchaseItemEntity implements Serializable {
    private static final long serialVersionUID = -6443604035583989142L;

    public PurchaseItemEntity() {
    }

    public PurchaseItemEntity(PurchaseEntity purchase, ProductEntity product,
                              @Min(value = 1, message = "Quantity must be greater than 0") Long quantity) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
    }

    public PurchaseItemEntity(ProductEntity product,
                              @Min(value = 1, message = "Quantity must be greater than 0") Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id")
    private Long purchaseItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseEntity purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
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
