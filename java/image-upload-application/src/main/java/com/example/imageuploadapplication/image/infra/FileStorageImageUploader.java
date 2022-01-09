package com.example.imageuploadapplication.image.infra;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.application.UploadImageRequest;
import com.example.imageuploadapplication.image.application.UploadImageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileStorageImageUploader implements ImageUploader {
    private final Path rootPath;

    @Override
    public UploadImageResponse upload(UploadImageRequest request) throws IOException {
        String filePath = getSavePath(request.getFileName());
        saveFile(request.getInputStream(), filePath);
        return new UploadImageResponse(request.getFileName());
    }

    private String getSavePath(String fileName) {
        return StringUtils.cleanPath(fileName);
    }

    private void saveFile(InputStream inputStream, String savePath) throws IOException {
        FileCopyUtils.copy(inputStream, new FileOutputStream(savePath));
    }
}
