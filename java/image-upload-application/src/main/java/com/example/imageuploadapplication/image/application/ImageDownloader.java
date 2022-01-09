package com.example.imageuploadapplication.image.application;

import java.io.IOException;

public interface ImageDownloader {
    DownloadImageResponse download(DownloadImageRequest request) throws IOException;
}
