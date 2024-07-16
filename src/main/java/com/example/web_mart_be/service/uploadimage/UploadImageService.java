package com.example.web_mart_be.service.uploadimage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface UploadImageService {
    String uploadImage(MultipartFile multipartFile, String name);
    void deleteImage(String imgUrl);
}
