package com.example.imageuploadapplication.image.infra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.example.imageuploadapplication.image.application.DownloadImageRequest;
import com.example.imageuploadapplication.image.application.DownloadImageResponse;
import com.example.imageuploadapplication.image.application.ImageDownloader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InMemoryImageDownloader implements ImageDownloader {
    private final ConcurrentHashMap<String, byte[]> imageMap;

    @Override
    public DownloadImageResponse download(DownloadImageRequest request) throws IOException {
        validateFileExist(request);
        return new DownloadImageResponse(imageMap.get(request.getFilePath()));
    }

    private void validateFileExist(DownloadImageRequest request) throws FileNotFoundException {
        if (!imageMap.containsKey(request.getFilePath())) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다. filePath=" + request.getFilePath());
        }
    }
}
