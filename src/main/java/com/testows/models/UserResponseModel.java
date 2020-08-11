package com.testows.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testows.entity.PurchaseEntity;

import java.util.List;

public class UserResponseModel {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private List<PurchaseEntity> purchases;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }
}
