package com.testows.entity;

import com.testows.models.Currency;
import com.testows.validators.ValueOfEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@DynamicUpdate
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = -5756681197560663971L;

    public ProductEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseItemEntity> purchaseItemEntities = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float price;

    @ValueOfEnum(enumClass = Currency.class)
    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String image = "default.png";

    private String weight;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<PurchaseItemEntity> getPurchaseItemEntities() {
        return purchaseItemEntities;
    }

    public void setPurchaseItemEntities(List<PurchaseItemEntity> purchaseItemEntities) {
        this.purchaseItemEntities = purchaseItemEntities;
    }

    public void addPurchaseItem(PurchaseItemEntity purchaseItemEntity) {
        purchaseItemEntities.add(purchaseItemEntity);
        purchaseItemEntity.setProduct(this);
    }
}
