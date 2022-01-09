package com.example.imageuploadapplication.image.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadImageService {
    private final ImageUploader imageUploader;

    public String uploadImage(MultipartFile file) throws IOException {
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String randomFileName = generateRandomFileName(originalFileName);
        InputStream inputStream = file.getInputStream();
        ImageUploadResponse uploadedFileUrl = imageUploader.upload(
                new ImageUploadRequest(inputStream, file.getSize(), randomFileName));
        return uploadedFileUrl.getImageUrl();
    }

    private String generateRandomFileName(String originalFileName) {
        return UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
    }
}
