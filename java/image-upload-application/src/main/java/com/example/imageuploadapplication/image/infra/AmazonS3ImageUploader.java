package com.example.imageuploadapplication.image.infra;

import java.io.InputStream;
import java.net.URL;

import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.application.UploadImageRequest;
import com.example.imageuploadapplication.image.application.UploadImageResponse;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
public class AmazonS3ImageUploader implements ImageUploader {
    private final S3Client s3Client;
    private final String bucket;

    @Override
    public UploadImageResponse upload(UploadImageRequest request) {
        PutObjectRequest putObjectRequest = createPutObjectRequest(request.getFileName());
        putObject(putObjectRequest, request.getInputStream(), request.getFileSize());
        String imageUrl = getUrl(request.getFileName());
        return new UploadImageResponse(imageUrl);
    }

    private PutObjectRequest createPutObjectRequest(String fileName) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(fileName)
                .build();
    }

    private void putObject(PutObjectRequest putObjectRequest, InputStream inputStream, Long fileSize) {
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, fileSize));
    }

    private String getUrl(String fileName) {
        URL url = s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(fileName).build());
        return url.toString();
    }
}
