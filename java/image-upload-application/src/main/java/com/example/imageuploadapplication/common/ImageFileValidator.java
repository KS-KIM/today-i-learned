package com.example.imageuploadapplication.common;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    @Override
    public void initialize(ImageFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String contentType = getContentType(file);
        return isSupportedContentType(contentType);
    }

    private String getContentType(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType(), "파일 형식을 찾을 수 없습니다.");
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/gif")
                || contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
