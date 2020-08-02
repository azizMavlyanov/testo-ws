package com.testows.models;

import javax.validation.constraints.NotBlank;

public class CategoryUpdateModel {
    @NotBlank(message = "Category name cannot be neither null nor blank")
    private String name;
    private String image;

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
