package com.lio.BlogApi.utils;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.lio.BlogApi.models.dtos.response.general.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<?> response(
            HttpStatus status,
            Integer statusCode,
            String message,
            T data) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .ok(true)
                .build();
    }

    public static <T> ApiResponse<?> errorResponse(
            HttpStatus status,
            Integer statusCode,
            String message,
            Map<String, String> error) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .statusCode(statusCode)
                .message(message)
                .ok(false)
                .error(error)
                .build();
    }

}
