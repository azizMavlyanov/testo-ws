package com.testows.models;

import com.testows.validators.ValueOfEnum;

public class PurchaseUpdateModel {
    @ValueOfEnum(enumClass = OrderStatus.class)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
