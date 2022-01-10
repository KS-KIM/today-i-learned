package com.example.imageuploadapplication.image.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.imageuploadapplication.common.ApiErrorResponse;
import com.example.imageuploadapplication.image.application.DownloadImageService;
import com.example.imageuploadapplication.image.application.UploadImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/images")
public class ImageController {
    private final UploadImageService uploadImageService;
    private final DownloadImageService downloadImageService;

    @PostMapping(
            value = "/upload",
            consumes = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_GIF_VALUE
            })
    public ResponseEntity<Void> uploadImage(MultipartFile file) throws IOException {
        String fileName = uploadImageService.uploadImage(file);
        return ResponseEntity.created(URI.create(fileName)).build();
    }

    @GetMapping(value = "/{fileName}")
    public ResponseEntity<byte[]> readImage(@PathVariable String fileName) throws IOException {
        byte[] imageFile = downloadImageService.downloadImage(fileName);
        MediaType mediaType = MediaTypeFactory.getMediaType(fileName)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 파일 형식입니다."));
        return ResponseEntity.ok().contentType(mediaType).body(imageFile);
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleFileNotFoundException(FileNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ApiErrorResponse> handleIOException(IOException exception) {
        return ResponseEntity.internalServerError().body(new ApiErrorResponse("파일을 처리하는 중 오류가 발생했습니다."));
    }
}
