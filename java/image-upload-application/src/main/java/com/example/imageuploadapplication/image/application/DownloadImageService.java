package com.example.imageuploadapplication.image.application;

import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DownloadImageService {
    private final ImageDownloader imageDownloader;

    public byte[] downloadImage(String fileName) throws IOException {
        DownloadImageResponse response = imageDownloader.download(new DownloadImageRequest(fileName));
        return response.getImage();
    }
}
