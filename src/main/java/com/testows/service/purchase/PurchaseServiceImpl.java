package com.testows.service.purchase;

import com.testows.dao.ProductRepository;
import com.testows.dao.PurchaseItemRepository;
import com.testows.dao.PurchaseRepository;
import com.testows.dao.UserRepository;
import com.testows.entity.CategoryEntity;
import com.testows.entity.PurchaseEntity;
import com.testows.entity.PurchaseItemEntity;
import com.testows.entity.UserEntity;
import com.testows.exceptions.CommonServiceException;
import com.testows.exceptions.ErrorMessages;
import com.testows.exceptions.ResourceNotFoundException;
import com.testows.models.*;
import com.testows.service.product.ProductService;
import com.testows.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseItemRepository purchaseItemRepository;
    private ProductRepository productRepository;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public PurchaseServiceImpl(ModelMapper modelMapper,
                               UserRepository userRepository,
                               PurchaseRepository purchaseRepository,
                               PurchaseItemRepository purchaseItemRepository,
                               ProductRepository productRepository,
                               UserService userService,
                               ProductService productService
                               ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public PurchaseEntity createOrder(Long userId, PurchaseRequestModel purchaseRequestModel) throws Exception {
        UserEntity userEntity = userService.findOne(userId);
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setAddress(purchaseRequestModel.getAddress());
        purchaseEntity.setStatus(purchaseRequestModel.getStatus());
        purchaseEntity.setUser(userEntity);

        for (PurchaseProductModel item : purchaseRequestModel.getProducts()) {
            purchaseEntity
                    .addPurchaseItem(new PurchaseItemEntity(productService.findOne(item.getProductId()),
                            item.getQuantity()));
        }

        PurchaseEntity createdPurchase = purchaseRepository.save(purchaseEntity);

        return createdPurchase;
    }

    @Override
    public PageableAndSortableData<PurchaseResponseModel> getOrders(Long userId, int page, int size) throws Exception {
        UserEntity userEntity = userService.findOne(userId);

        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<PurchaseEntity> purchaseEntities = purchaseRepository.findByUser(userEntity, pageableRequest);

        PageableAndSortableData<PurchaseResponseModel> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(purchaseEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(purchaseEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(purchaseEntities.hasPrevious());
        pagedAndSortedData.setHasNext(purchaseEntities.hasNext());
        pagedAndSortedData.setTotalElements(purchaseEntities.getTotalElements());
        pagedAndSortedData.setSort(purchaseEntities.getSort().toString());

        Type listType = new TypeToken<List<PurchaseResponseModel>>() {
        }.getType();
        List<PurchaseResponseModel> purchaseResponseList = modelMapper.map(purchaseEntities.getContent(), listType);

        pagedAndSortedData.setData(purchaseResponseList);

        return pagedAndSortedData;
    }

    @Override
    public PurchaseEntity findOne(Long userId, Long purchaseId) throws Exception{
        userService.findOne(userId);

        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    @Override
    public PurchaseEntity update(Long userId, Long purchaseId, PurchaseUpdateModel purchaseUpdateModel) throws Exception {
        PurchaseEntity purchaseEntity = this.findOne(userId, purchaseId);

        modelMapper.map(purchaseUpdateModel, purchaseEntity);

        try {
            return purchaseRepository.save(purchaseEntity);
        } catch (Exception e) {
            throw new CommonServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public PageableAndSortableData<PurchaseItemResponse> getPurchaseItems(Long userId,
                                                                          Long purchaseId,
                                                                          int page, int size) throws Exception
    {
        PurchaseEntity purchaseEntity = this.findOne(userId, purchaseId);

        if (page != 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, size);
        Page<PurchaseItemEntity> purchaseItemEntities = null;
        try {
            purchaseItemEntities = purchaseItemRepository
                    .findByPurchase(purchaseEntity, pageableRequest);
        } catch (Exception e) {
            throw new CommonServiceException(e.getLocalizedMessage());
        }

        PageableAndSortableData<PurchaseItemResponse> pagedAndSortedData = new PageableAndSortableData<>();
        pagedAndSortedData.setPage(purchaseItemEntities.getPageable().getPageNumber() + 1);
        pagedAndSortedData.setSize(purchaseItemEntities.getPageable().getPageSize());
        pagedAndSortedData.setHasPrevious(purchaseItemEntities.hasPrevious());
        pagedAndSortedData.setHasNext(purchaseItemEntities.hasNext());
        pagedAndSortedData.setTotalElements(purchaseItemEntities.getTotalElements());
        pagedAndSortedData.setSort(purchaseItemEntities.getSort().toString());

        Type listType = new TypeToken<List<PurchaseItemResponse>>() {
        }.getType();
        List<PurchaseItemResponse> purchaseItemResponseList = modelMapper.map(purchaseItemEntities.getContent(), listType);

        pagedAndSortedData.setData(purchaseItemResponseList);

        return pagedAndSortedData;
    }
}
