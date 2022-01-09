package com.example.imageuploadapplication.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.imageuploadapplication.image.infra.FileStorageImageUploader;

@Profile({"local", "dev"})
@Configuration
public class FileStorageImageUploaderConfiguration {
    @Bean
    public FileStorageImageUploader fileStorageImageUploader() {
        return new FileStorageImageUploader(absolutePath());
    }

    private Path absolutePath() {
        return Paths.get("uploads");
    }
}
