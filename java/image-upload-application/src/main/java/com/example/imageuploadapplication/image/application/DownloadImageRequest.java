package com.example.imageuploadapplication.image.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadImageRequest {
    private final String filePath;
}
