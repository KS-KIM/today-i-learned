package com.example.imageuploadapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.imageuploadapplication.image.application.ImageDownloader;
import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.infra.FileStorageImageDownloader;
import com.example.imageuploadapplication.image.infra.FileStorageImageUploader;

@Profile("dev")
@Configuration
public class FileStorageImageStorageConfiguration {
    @Bean
    public ImageUploader fileStorageImageUploader() {
        return new FileStorageImageUploader();
    }

    @Bean
    public ImageDownloader imageDownloader() {
        return new FileStorageImageDownloader();
    }
}
