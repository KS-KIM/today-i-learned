package com.example.imageuploadapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.imageuploadapplication.image.application.ImageDownloader;
import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.infra.AmazonS3ImageDownloader;
import com.example.imageuploadapplication.image.infra.AmazonS3ImageUploader;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Profile("prod")
@Configuration
public class AmazonS3Configuration {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(region())
                .credentialsProvider(credentialProvider())
                .build();
    }

    @Bean
    public ImageUploader imageUploader(S3Client s3Client) {
        return new AmazonS3ImageUploader(s3Client, bucket);
    }

    @Bean
    public ImageDownloader imageDownloader(S3Client s3Client) {
        return new AmazonS3ImageDownloader(s3Client, bucket);
    }

    private Region region() {
        return Region.of(region);
    }

    private AwsCredentialsProvider credentialProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }
}
