package com.testows.service.purchase;

import com.testows.dao.ProductRepository;
import com.testows.dao.PurchaseItemRepository;
import com.testows.dao.PurchaseRepository;
import com.testows.dao.UserRepository;
import com.testows.entity.PurchaseEntity;
import com.testows.entity.PurchaseItemEntity;
import com.testows.entity.UserEntity;
import com.testows.models.PurchaseProductModel;
import com.testows.models.PurchaseRequestModel;
import com.testows.models.PurchaseResponseModel;
import com.testows.service.product.ProductService;
import com.testows.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PurchaseEntity createOrder(Long userId, PurchaseRequestModel purchaseRequestModel) {
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

        PurchaseEntity purchaseEntity1 = purchaseRepository.save(purchaseEntity);

        return purchaseEntity1;
    }
}
