package com.testows.service.category;

import com.testows.dao.CategoryRepository;
import com.testows.entity.CategoryEntity;
import com.testows.models.PageableAndSortableData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
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
}
