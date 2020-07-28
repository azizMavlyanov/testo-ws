package com.testows.models;

import com.testows.validators.ValueOfEnum;

import javax.validation.constraints.NotNull;

public class ProductRequestModel {
    @NotNull(message = "Category name be null")
    private String name;
    @NotNull(message = "Category price be null")
    private Float price;
    @ValueOfEnum(enumClass = Currency.class)
    @NotNull(message = "Currency value cannot be null")
    private String currency = Currency.RUB.name();
    private Boolean active = true;
    private String image = "images/defaults/product.jpg";
    private String weight;
    private String description;

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
}
