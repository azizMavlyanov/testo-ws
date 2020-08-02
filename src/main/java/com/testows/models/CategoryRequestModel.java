package com.testows.models;

import javax.validation.constraints.NotBlank;

public class CategoryRequestModel {
    @NotBlank(message = "Category name cannot be neither null nor blank")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
