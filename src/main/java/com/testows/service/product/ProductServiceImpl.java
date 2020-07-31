package com.testows.service.product;

import com.testows.dao.ProductRepository;
import com.testows.entity.ProductEntity;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity findOne(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Error("Product not found"));
    }

    @Override
    public void uploadImage(Long productId, MultipartFile file) throws IOException {
        ProductEntity productEntity = this.findOne(productId);

        Path productImagesDirectory = Paths.get("src", "main", "resources",
                "static", "assets", "images", "products");
        String absolutePath = productImagesDirectory.toFile().getAbsolutePath();

        Path copyLocation = Path
                .of(absolutePath,
                        StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));

        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        Thumbnails.of(copyLocation.toString()).size(150, 150).outputFormat("jpg").toFiles(Rename.NO_CHANGE);
    }

    @Override
    public Resource loadImage(Long productId, String imageName) throws MalformedURLException {
        Path productImagesDirectory = Paths.get("src", "main", "resources",
                "static", "assets", "images", "products");
        String absolutePath = productImagesDirectory.toFile().getAbsolutePath();
        Path copyLocation = Path
                .of(absolutePath, imageName);

        Resource resource = new UrlResource(copyLocation.toUri());

        return resource;
    }
}
