package com.example.imageuploadapplication.image.infra;

import java.io.IOException;

import com.example.imageuploadapplication.image.application.DownloadImageRequest;
import com.example.imageuploadapplication.image.application.DownloadImageResponse;
import com.example.imageuploadapplication.image.application.ImageDownloader;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RequiredArgsConstructor
public class AmazonS3ImageDownloader implements ImageDownloader {
    private final S3Client s3Client;
    private final String bucket;

    @Override
    public DownloadImageResponse download(DownloadImageRequest request) throws IOException {
        GetObjectRequest getObjectRequest = createGetObjectRequest(request.getFilePath());
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);
        byte[] image = object.readAllBytes();
        object.close();
        return new DownloadImageResponse(image);
    }

    private GetObjectRequest createGetObjectRequest(String filePath) {
        return GetObjectRequest.builder().bucket(bucket).key(filePath).build();
    }
}
