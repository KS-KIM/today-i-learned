package com.example.imageuploadapplication.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiErrorResponse {
    private final String message;
}
