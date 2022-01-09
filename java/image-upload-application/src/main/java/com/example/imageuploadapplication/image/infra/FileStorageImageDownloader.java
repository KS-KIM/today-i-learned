package com.example.imageuploadapplication.image.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.util.StringUtils;

import com.example.imageuploadapplication.image.application.DownloadImageRequest;
import com.example.imageuploadapplication.image.application.DownloadImageResponse;
import com.example.imageuploadapplication.image.application.ImageDownloader;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.utils.IoUtils;

@RequiredArgsConstructor
public class FileStorageImageDownloader implements ImageDownloader {
    private final Path rootPath;

    @Override
    public DownloadImageResponse download(DownloadImageRequest request) throws IOException {
        String savePath = getSavePath(request.getFilePath());
        byte[] file = readFile(savePath);
        return new DownloadImageResponse(file);
    }

    private String getSavePath(String fileName) {
        return StringUtils.cleanPath(fileName);
    }

    private byte[] readFile(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        return IoUtils.toByteArray(inputStream);
    }
}
