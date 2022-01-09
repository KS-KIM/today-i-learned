package com.example.imageuploadapplication.image.application;

import java.io.IOException;

public interface ImageUploader {
    UploadImageResponse upload(final UploadImageRequest request) throws IOException;
}
