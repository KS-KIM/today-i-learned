package com.example.imageuploadapplication.image.ui;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.imageuploadapplication.image.application.UploadImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/images")
public class ImageController {
    private final UploadImageService uploadImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(MultipartFile file) throws IOException {
        String url = uploadImageService.uploadImage(file);
        return ResponseEntity.ok(url);
    }
}
