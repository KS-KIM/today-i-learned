package com.example.imageuploadapplication.image.application;

import java.io.InputStream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageUploadRequest {
    private final InputStream inputStream;
    private final Long fileSize;
    private final String fileName;
}
