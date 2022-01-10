package com.example.imageuploadapplication.common;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiErrorResponse handleIllegalArgumentException(ConstraintViolationException exception) {
        log.error(exception.getMessage());
        return new ApiErrorResponse("요청이 올바르지 않습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ApiErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage());
        return new ApiErrorResponse("요청이 올바르지 않습니다.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = FileNotFoundException.class)
    public ApiErrorResponse handleFileNotFoundException(FileNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiErrorResponse("파일을 찾을 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IOException.class)
    public ApiErrorResponse handleIOException(IOException exception) {
        log.error(exception.getMessage());
        return new ApiErrorResponse("파일을 처리하는 중 오류가 발생했습니다.");
    }
}
