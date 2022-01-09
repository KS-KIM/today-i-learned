package com.example.imageuploadapplication.image.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DownloadImageResponse {
    private final byte[] image;
}
