package com.testows.models;

import com.testows.validators.ValueOfEnum;

import java.util.List;

import javax.validation.constraints.NotNull;

public class PurchaseRequestModel {
    @NotNull(message = "Address cannot be null")
    private String address;
    @ValueOfEnum(enumClass = OrderStatus.class)
    @NotNull(message = "Status cannot be null")
    private String status = OrderStatus.open.name();
    @NotNull(message = "Products cannot be null")
    private List<PurchaseProductModel> products;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<PurchaseProductModel> products) {
        this.products = products;
    }
}
