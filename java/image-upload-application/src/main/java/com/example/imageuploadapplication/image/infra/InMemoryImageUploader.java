package com.example.imageuploadapplication.image.infra;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.application.UploadImageRequest;
import com.example.imageuploadapplication.image.application.UploadImageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemoryImageUploader implements ImageUploader {
    private final ConcurrentHashMap<String, byte[]> imageMap;

    @Override
    public UploadImageResponse upload(UploadImageRequest request) throws IOException {
        imageMap.put(request.getFileName(), request.getInputStream().readAllBytes());
        return new UploadImageResponse(request.getFileName());
    }
}
