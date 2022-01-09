package com.example.imageuploadapplication.image.infra;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.util.FileCopyUtils;

import com.example.imageuploadapplication.image.application.ImageUploadRequest;
import com.example.imageuploadapplication.image.application.ImageUploadResponse;
import com.example.imageuploadapplication.image.application.ImageUploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileStorageImageUploader implements ImageUploader {
    private final Path rootPath;

    @Override
    public ImageUploadResponse upload(ImageUploadRequest request) throws IOException {
        Path savePath = getSavePath(request.getFileName());
        saveFile(request.getInputStream(), savePath);
        return new ImageUploadResponse(request.getFileName());
    }

    private Path getSavePath(String fileName) {
        return rootPath.resolve(fileName);
    }

    private void saveFile(InputStream inputStream, Path savePath) throws IOException {
        FileCopyUtils.copy(inputStream, new FileOutputStream(savePath.toFile()));
    }
}
