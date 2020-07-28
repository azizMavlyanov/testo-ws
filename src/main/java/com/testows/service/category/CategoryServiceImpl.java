package com.testows.service.category;

import com.testows.dao.CategoryRepository;
import com.testows.dao.ProductRepository;
import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.models.PageableAndSortableData;
import com.testows.models.ProductResponseModel;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryEntity create(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public PageableAndSortableData<CategoryEntity> findAll(int page, int size) {
        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageableRequest);

        PageableAndSortableData<CategoryEntity> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(categoryEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(categoryEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(categoryEntities.hasPrevious());
        pagedAndSortedData.setHasNext(categoryEntities.hasNext());
        pagedAndSortedData.setTotalElements(categoryEntities.getTotalElements());
        pagedAndSortedData.setSort(categoryEntities.getSort().toString());
        pagedAndSortedData.setData(categoryEntities.getContent());

        return pagedAndSortedData;
    }

    @Override
    public CategoryEntity findOne(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new Error("Category not found"));
    }

    @Override
    public CategoryEntity update(Long categoryId, CategoryEntity categoryEntity) {
        CategoryEntity categoryInDB = this.findOne(categoryId);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(categoryEntity, categoryInDB);

        return categoryRepository.save(categoryInDB);
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepository.delete(this.findOne(categoryId));
    }

    @Override
    public ProductEntity addProduct(Long categoryId, ProductEntity productEntity) {
        CategoryEntity categoryEntity = findOne(categoryId);
        productEntity.setCategory(categoryEntity);

        return productRepository.save(productEntity);
    }

    @Override
    public PageableAndSortableData<ProductResponseModel> getProducts(Long categoryId, int page, int size) {
        CategoryEntity categoryEntity = this.findOne(categoryId);

        List<ProductResponseModel> products = new ArrayList<>();

        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<ProductEntity> productEntities = productRepository.findByCategory(categoryEntity, pageableRequest);

        for (ProductEntity productEntity : productEntities.getContent()) {
            products.add(modelMapper.map(productEntity, ProductResponseModel.class));
        }

        PageableAndSortableData<ProductResponseModel> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(productEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(productEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(productEntities.hasPrevious());
        pagedAndSortedData.setHasNext(productEntities.hasNext());
        pagedAndSortedData.setTotalElements(productEntities.getTotalElements());
        pagedAndSortedData.setSort(productEntities.getSort().toString());
        pagedAndSortedData.setData(products);

        return pagedAndSortedData;
    }
}
