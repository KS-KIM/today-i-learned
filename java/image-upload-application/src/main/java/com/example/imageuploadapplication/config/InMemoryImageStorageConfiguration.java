package com.example.imageuploadapplication.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.imageuploadapplication.image.application.ImageDownloader;
import com.example.imageuploadapplication.image.application.ImageUploader;
import com.example.imageuploadapplication.image.infra.InMemoryImageDownloader;
import com.example.imageuploadapplication.image.infra.InMemoryImageUploader;

@Profile({"local", "test"})
@Configuration
public class InMemoryImageStorageConfiguration {
    @Bean
    public ConcurrentHashMap<String, byte[]> imageMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ImageUploader inMemoryImageUploader(ConcurrentHashMap<String, byte[]> imageMap) {
        return new InMemoryImageUploader(imageMap);
    }

    @Bean
    public ImageDownloader imageDownloader(ConcurrentHashMap<String, byte[]> imageMap) {
        return new InMemoryImageDownloader(imageMap);
    }
}
