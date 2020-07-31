package com.testows.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile file);
}
