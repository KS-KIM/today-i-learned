package com.example.imageuploadapplication.image.ui;

import java.io.IOException;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.imageuploadapplication.image.application.DownloadImageService;
import com.example.imageuploadapplication.image.application.UploadImageCommand;
import com.example.imageuploadapplication.image.application.UploadImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/images")
public class ImageController {
    private final UploadImageService uploadImageService;
    private final DownloadImageService downloadImageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@Valid UploadImageCommand command) throws IOException {
        String fileName = uploadImageService.uploadImage(command);
        return ResponseEntity.created(URI.create(fileName)).build();
    }

    @GetMapping(value = "/{fileName}")
    public ResponseEntity<byte[]> readImage(@PathVariable String fileName) throws IOException {
        byte[] imageFile = downloadImageService.downloadImage(fileName);
        MediaType mediaType = MediaTypeFactory.getMediaType(fileName)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 파일 형식입니다."));
        return ResponseEntity.ok().contentType(mediaType).body(imageFile);
    }
}
