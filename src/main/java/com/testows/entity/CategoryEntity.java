package com.testows.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
@DynamicUpdate
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 6580629678611086685L;

    public CategoryEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String image;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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
