package com.example.web_mart_be.service.uploadimage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadImageImp implements UploadImageService{
    @Override
    public String uploadImage(MultipartFile multipartFile, String name) {
        return null;
    }

    @Override
    public void deleteImage(String imgUrl) {

    }
}
