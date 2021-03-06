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

    public String uploadImage(UploadImageCommand command) throws IOException {
        MultipartFile file = command.getFile();
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String saveFileName = generateRandomFileName(originalFileName);
        InputStream inputStream = file.getInputStream();
        imageUploader.upload(new UploadImageRequest(inputStream, file.getSize(), saveFileName));
        return saveFileName;
    }

    private String generateRandomFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}
