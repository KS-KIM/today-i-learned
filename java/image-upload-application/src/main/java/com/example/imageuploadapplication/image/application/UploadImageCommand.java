package com.example.imageuploadapplication.image.application;

import org.springframework.web.multipart.MultipartFile;

import com.example.imageuploadapplication.common.ImageFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UploadImageCommand {
    @ImageFile()
    private final MultipartFile file;
}
