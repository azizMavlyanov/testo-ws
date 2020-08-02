package com.testows.service.category;

import com.testows.dao.CategoryRepository;
import com.testows.dao.ProductRepository;
import com.testows.entity.CategoryEntity;
import com.testows.entity.ProductEntity;
import com.testows.exceptions.CategoryServiceException;
import com.testows.exceptions.ErrorMessages;
import com.testows.exceptions.ResourceAlreadyExistsException;
import com.testows.exceptions.ResourceNotFoundException;
import com.testows.models.CategoryResponseModel;
import com.testows.models.PageableAndSortableData;
import com.testows.models.ProductResponseModel;
import com.testows.models.UserResponseModel;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public CategoryEntity create(CategoryEntity categoryEntity) throws Exception {

        if (categoryRepository.findByName(categoryEntity.getName()) != null) {
            throw new ResourceAlreadyExistsException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        try {
            return categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            throw new CategoryServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public PageableAndSortableData<CategoryResponseModel> findAll(int page, int size) {
        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageableRequest);

        PageableAndSortableData<CategoryResponseModel> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(categoryEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(categoryEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(categoryEntities.hasPrevious());
        pagedAndSortedData.setHasNext(categoryEntities.hasNext());
        pagedAndSortedData.setTotalElements(categoryEntities.getTotalElements());
        pagedAndSortedData.setSort(categoryEntities.getSort().toString());

        Type listType = new TypeToken<List<CategoryResponseModel>>() {
        }.getType();
        List<CategoryResponseModel> categoryResponseList = modelMapper.map(categoryEntities.getContent(), listType);

        pagedAndSortedData.setData(categoryResponseList);

        return pagedAndSortedData;
    }

    @Override
    public CategoryEntity findOne(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    @Override
    public CategoryEntity update(Long categoryId, CategoryEntity categoryEntity) throws Exception {
        CategoryEntity categoryInDB = this.findOne(categoryId);

        if (categoryRepository.findByName(categoryEntity.getName()) != null) {
            throw new ResourceAlreadyExistsException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        modelMapper.map(categoryEntity, categoryInDB);

        try {
            return categoryRepository.save(categoryInDB);
        } catch (Exception e) {
            throw new CategoryServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepository.delete(this.findOne(categoryId));
    }

    @Override
    public void uploadImage(Long categoryId, MultipartFile file) throws Exception {
        CategoryEntity categoryEntity = this.findOne(categoryId);

        Path categoryImagesDirectory = Paths.get("src", "main", "resources",
                "static", "assets", "images", "categories");
        String absolutePath = categoryImagesDirectory.toFile().getAbsolutePath();

        Path copyLocation = Path
                .of(absolutePath,
                        StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));

        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        Thumbnails.of(copyLocation.toString()).size(70, 70).outputFormat("jpg").toFiles(Rename.NO_CHANGE);
    }

    @Override
    public Resource loadImage(Long categoryId, String imageName) throws Exception {
        Path categoryImagesDirectory = Paths.get("src", "main", "resources",
                "static", "assets", "images", "categories");
        String absolutePath = categoryImagesDirectory.toFile().getAbsolutePath();
        Path copyLocation = Path
                .of(absolutePath, imageName);

        Resource resource = new UrlResource(copyLocation.toUri());

        return resource;

    }

    @Override
    public ProductEntity addProduct(Long categoryId, ProductEntity productEntity) throws Exception {
        CategoryEntity categoryEntity = findOne(categoryId);
        productEntity.setCategory(categoryEntity);

        try {
            return productRepository.save(productEntity);
        } catch (Exception e) {
            throw new CategoryServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public PageableAndSortableData<ProductResponseModel> getProducts(Long categoryId, int page, int size) {
        CategoryEntity categoryEntity = this.findOne(categoryId);

        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<ProductEntity> productEntities = productRepository.findByCategory(categoryEntity, pageableRequest);

        PageableAndSortableData<ProductResponseModel> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(productEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(productEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(productEntities.hasPrevious());
        pagedAndSortedData.setHasNext(productEntities.hasNext());
        pagedAndSortedData.setTotalElements(productEntities.getTotalElements());
        pagedAndSortedData.setSort(productEntities.getSort().toString());

        Type listType = new TypeToken<List<ProductResponseModel>>() {
        }.getType();
        List<ProductResponseModel> products = modelMapper.map(productEntities.getContent(), listType);

        pagedAndSortedData.setData(products);

        return pagedAndSortedData;
    }

    @Override
    public ProductEntity getProduct(Long categoryId, Long productId) {
        this.findOne(categoryId);

        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    @Override
    public ProductEntity updateProduct(Long categoryId, Long productId, ProductEntity productEntity) throws Exception {
        this.findOne(categoryId);
        ProductEntity productInDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        modelMapper.map(productEntity, productInDB);

        try {
            return productRepository.save(productInDB);
        } catch (Exception e) {
            throw new CategoryServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteProduct(Long categoryId, Long productId) throws Exception {
        ProductEntity productEntity = this.getProduct(categoryId, productId);

        try {
            productRepository.deleteById(productEntity.getProductId());
        } catch (Exception e) {
            throw new CategoryServiceException(e.getLocalizedMessage());
        }
    }
}
