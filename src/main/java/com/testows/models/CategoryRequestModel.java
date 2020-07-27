package com.testows.models;

import javax.validation.constraints.NotNull;

public class CategoryRequestModel {
    @NotNull(message = "Category name cannot be null")
    private String name;
    @NotNull(message = "Category image cannot be null")
    private String image = "/images/defaults/category.jpg";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
