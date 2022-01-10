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
    private static final String SUPPORT_CONTENT_TYPE = "image";

    private final ImageUploader imageUploader;

    public String uploadImage(MultipartFile file) throws IOException {
        validateContentType(file);
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String saveFileName = generateRandomFileName(originalFileName);
        InputStream inputStream = file.getInputStream();
        imageUploader.upload(new UploadImageRequest(inputStream, file.getSize(), saveFileName));
        return saveFileName;
    }

    private void validateContentType(MultipartFile file) {
        String contentType = Objects.requireNonNull(file.getContentType(), "파일 형식을 찾을 수 없습니다.");
        if (!contentType.startsWith(SUPPORT_CONTENT_TYPE)) {
            throw new IllegalArgumentException("파일 형식이 올바르지 않습니다. contentType=" + file.getContentType());
        }
    }

    private String generateRandomFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}
