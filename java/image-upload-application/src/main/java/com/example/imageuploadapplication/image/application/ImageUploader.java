package com.example.imageuploadapplication.image.application;

import java.io.IOException;

public interface ImageUploader {
    ImageUploadResponse upload(final ImageUploadRequest request) throws IOException;
}
