package com.testows.service.category;

import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.CategoryResponseModel;
import com.testows.models.PageableAndSortableData;
import com.testows.models.ProductResponseModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryEntity create(CategoryEntity categoryEntity) throws Exception;
    PageableAndSortableData<CategoryResponseModel> findAll(int page, int size);
    CategoryEntity findOne(Long categoryId);
    CategoryEntity update(Long categoryId, CategoryEntity categoryEntity) throws Exception;
    void delete(Long categoryId);
    void uploadImage(Long categoryId, MultipartFile file) throws Exception;
    Resource loadImage(Long categoryId, String imageName) throws Exception;
    ProductEntity addProduct(Long categoryId, ProductEntity productEntity) throws Exception;
    PageableAndSortableData<ProductResponseModel> getProducts(Long categoryId, int page, int size);
    ProductEntity updateProduct(Long categoryId, Long productId, ProductEntity productEntity) throws Exception;
    ProductEntity getProduct(Long categoryId, Long productId);
    void deleteProduct(Long categoryId, Long productId) throws Exception;
}
